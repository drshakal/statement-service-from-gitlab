package in.co.dhdigital.processors;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;

public class getHeaderProcessor implements Processor {

	@Override
	public void process(Exchange exchange) throws Exception {
		String key=exchange.getIn().getHeader("accountnumber", String.class);
		//exchange.getIn().setHeader("CacheConstants.CACHE_OPERATION", "CacheConstants.CACHE_OPERATION_GET");
	    //exchange.getIn().setHeader("CacheConstants.CACHE_KEY", key);
	    exchange.getIn().setHeader("CamelCacheOperation", "CamelCacheGet");
	    exchange.getIn().setHeader("CamelCacheKey", key);
	    
	    System.out.print("get processor called--------");
	}

}
