package in.co.dhdigital.aggregators;

import java.util.List;

import org.apache.camel.Exchange;
import org.apache.camel.processor.aggregate.AggregationStrategy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import in.co.dhdigital.models.AccountDetails;
import in.co.dhdigital.models.AccountDetailsResponse;
import in.co.dhdigital.models.FaultType;
import in.co.dhdigital.models.RestartResponse;
import in.co.dhdigital.models.StatementServiceResponse;
import in.co.dhdigital.models.TransactionDetail;

public class AccountDetailsAggregator implements AggregationStrategy {
	static Logger log =LoggerFactory.getLogger(AccountDetailsAggregator.class);

	@Override
	public Exchange aggregate(Exchange oldExchange, Exchange newExchange) {
		log.info("Inside AccountDetailsAggregator.aggregate()");
		

		AccountDetailsResponse accountDetailsResponse= oldExchange.getIn().getBody(AccountDetailsResponse.class);
		
		StatementServiceResponse response = new StatementServiceResponse();
		FaultType fault = new FaultType();
		boolean faultOccurred = false;
		if(accountDetailsResponse != null) {
			faultOccurred = accountDetailsResponse.isFaultOccurred();
			log.info("faultOccurred : "+ faultOccurred);
		}
		if (accountDetailsResponse == null || faultOccurred) {
			 fault = oldExchange.getIn().getBody(FaultType.class);
			 if(fault != null) {
				 response.setFault(fault);
				 response.setFaultOccurred(faultOccurred);
				 oldExchange.getIn().setBody(response);
				 return oldExchange; 
			 }			 
			 else {
				 fault= handleFault("Error while accounts Service", "Internal Server error");
				 response.setFault(fault);
				 response.setFaultOccurred(faultOccurred);
				 oldExchange.getIn().setBody(response);
				 return oldExchange; 
			 }
		}
		RestartResponse restartResponse = newExchange.getIn().getBody(RestartResponse.class);
		if (restartResponse == null ) {
			fault.setType("Error while calling restart service.");
			fault.setDescription("Internal Server Error");
			response.setFault(fault);
			response.setFaultOccurred(faultOccurred);
			oldExchange.getIn().setBody(response);
			return oldExchange; 
		}
		AccountDetails accountDetails = accountDetailsResponse.getAccountDetails();
		List<TransactionDetail> transactionDetails = accountDetailsResponse.getTransactionDetail();
				
		response.setAccountDetails(accountDetails);
		response.setTransactionDetails(transactionDetails);
		response.setRestart(restartResponse.getRestart());
		response.setFaultOccurred(false);
		oldExchange.getIn().setBody(response);
		return oldExchange;
	}
		public FaultType handleFault( String e, String type) {
			
			FaultType fault = new FaultType();
			
			fault.setDescription(e);
			fault.setType(type);
			fault.setSystem("ESB");
			fault.setNativeDescription(e);
			fault.setNumber(500);
			fault.setRetryAfter("Two mins");
			return fault;
		}
}
