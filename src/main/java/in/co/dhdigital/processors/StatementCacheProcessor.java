package in.co.dhdigital.processors;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import in.co.dhdigital.models.StatementServiceResponse;
import in.co.dhdigital.repositories.StatementServiceDao;

public class StatementCacheProcessor implements Processor {
	static Logger log =LoggerFactory.getLogger(StatementCacheProcessor.class);

	@Autowired
	private  StatementServiceDao statementServiceDao;
	
	@Override
	public void process(Exchange exchange) throws Exception {
		log.info("Inside StatementCacheProcessor.process()");
		
		StatementServiceResponse response = new StatementServiceResponse();
		response = exchange.getIn().getBody(StatementServiceResponse.class);
		statementServiceDao.setStatementResponse(response);
		

	}

}
