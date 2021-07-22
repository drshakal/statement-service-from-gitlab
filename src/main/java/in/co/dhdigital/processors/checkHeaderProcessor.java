package in.co.dhdigital.processors;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;

public class checkHeaderProcessor implements Processor {

	@Override
	public void process(Exchange exchange) throws Exception {
		int key=exchange.getIn().getHeader("id", Integer.class);
		exchange.getIn().setHeader("CacheConstants.CACHE_OPERATION", "CacheConstants.CACHE_OPERATION_CHECK");
	    exchange.getIn().setHeader("CacheConstants.CACHE_KEY", key);
	    exchange.getIn().setHeader("CamelCacheOperation", "CamelCacheCheck");
	    exchange.getIn().setHeader("CamelCacheKey", key);
	    System.out.print("check processor called--------");

	}

}
