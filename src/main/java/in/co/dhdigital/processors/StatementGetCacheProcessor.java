package in.co.dhdigital.processors;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import in.co.dhdigital.models.StatementServiceResponse;
import in.co.dhdigital.repositories.StatementServiceDao;

public class StatementGetCacheProcessor implements Processor {
	static Logger log =LoggerFactory.getLogger(StatementGetCacheProcessor.class);

	@Autowired
	private StatementServiceDao dao;
	
	@Override
	public void process(Exchange ex) throws Exception {
		log.info("Inside StatementGetCacheProcessor.process()");
		String accountNumber = ex.getIn().getHeader("accountnumber", String.class);
		log.info("accountnumber: "+ accountNumber);
		
		
		StatementServiceResponse response = dao.findStatementResponseById(accountNumber);
		//log.info("CacheResponse: "+ response.getAccountDetails().getAccountNumber());
		if(response != null) {
			ex.getIn().setHeader("found", "true");
			ex.getIn().setBody(response);
			return;
		}
		ex.getIn().setHeader("found", "false");
	}

}
