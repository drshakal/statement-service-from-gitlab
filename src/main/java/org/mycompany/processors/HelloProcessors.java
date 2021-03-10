package org.mycompany.processors;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.mycompany.models.Request;

public class HelloProcessors implements Processor {

	@Override
	public void process(Exchange exchange) throws Exception {
		Request req = exchange.getIn().getBody(Request.class);
		
		System.out.println("req.name : "+ req.getName());
		
		String message = "hello, ";
		message += req.getName();
		
		exchange.getIn().setBody(message);
		exchange.getIn().setHeader("Content-Type", "application/json");
	}

}
