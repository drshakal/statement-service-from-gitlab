package in.co.dhdigital.processors;



import org.apache.camel.Exchange;
import org.apache.camel.Processor;

import in.co.dhdigital.models.AccountDetails;
import in.co.dhdigital.models.StatementServiceResponse;

public class addHeaderProcessor implements Processor {

	@Override
	public void process(Exchange exchange) throws Exception {
		StatementServiceResponse statementServiceResponse;
		String key="";
		try {
			statementServiceResponse = exchange.getIn().getBody(StatementServiceResponse.class);
			AccountDetails accountDetails=statementServiceResponse.getAccountDetails();
			key=accountDetails.getAccountNumber();
			System.out.print("to (accountnumber)string key :"+statementServiceResponse+":--------");
			
		} catch (Exception e) {
			System.out.print("NULL OBJ");
		}
		
		//exchange.getIn().setHeader("CacheConstants.CACHE_OPERATION", "CacheConstants.CACHE_OPERATION_ADD");
	    //exchange.getIn().setHeader("CacheConstants.CACHE_KEY", key);
	    
	    exchange.getIn().setHeader("CamelCacheOperation", "CamelCacheAdd");
	    exchange.getIn().setHeader("CamelCacheKey", key);
	    
	    System.out.print("add processor called--------");

	}

}
