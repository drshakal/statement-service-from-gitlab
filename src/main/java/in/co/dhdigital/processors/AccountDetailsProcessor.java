package in.co.dhdigital.processors;


import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import in.co.dhdigital.models.AccountDetailsResponse;
import in.co.dhdigital.models.FaultType;

public class AccountDetailsProcessor implements Processor{
	static Logger log =LoggerFactory.getLogger(AccountDetailsProcessor.class);

	@Override
	public void process(Exchange ex) throws Exception {

		log.info("Inside AccountDetailsProcessor.process()");
		try {
			 
			AccountDetailsResponse accountDetails =  ex.getIn().getBody(AccountDetailsResponse.class);
			if (accountDetails != null ) {
				if(accountDetails.isFaultOccurred()) {
					FaultType fault = accountDetails.getFault();
					ex.getIn().setBody(fault);
					ex.getIn().setHeader("faultOccurred", true);
					return;
				}
				ex.getIn().setHeader("faultOccurred", false);
				ex.getIn().setBody(accountDetails.getAccountDetails());
			}else {
				log.warn("Error while calling account-details service");
				ex.getIn().setHeader("faultOccurred", true);
				
				handleFault(ex);
			}
		}catch (Exception e) {
			log.error("Error : "+ e.toString());
			handleFault(ex);
		}

	}

	public void handleFault(Exchange ex) {
		FaultType fault = new FaultType();
		
		fault.setDescription("No Record Found");
		fault.setType("Client Exception");
		fault.setSystem("ESB");
		fault.setNativeDescription("No Record Found");
		fault.setNumber(401);
		fault.setRetryAfter("Two mins");
		
		ex.getIn().setHeader("faultOccurred", true);

		ex.getIn().setBody(fault);
	}
	
}
