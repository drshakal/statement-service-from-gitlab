package in.co.dhdigital.routes;

import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.jackson.JacksonDataFormat;
import org.apache.camel.model.dataformat.JsonLibrary;
import org.apache.camel.model.rest.RestBindingMode;
import org.apache.camel.processor.aggregate.AggregationStrategy;
import org.springframework.stereotype.Component;

import in.co.dhdigital.aggregators.AccountDetailsAggregator;
import in.co.dhdigital.models.AccountDetailsResponse;
import in.co.dhdigital.models.RestartResponse;
//import in.co.dhdigital.models.StatementServiceResponse;
//import in.co.dhdigital.processors.StatementCacheProcessor;
//import in.co.dhdigital.processors.StatementGetCacheProcessor;
import in.co.dhdigital.models.StatementServiceResponse;
import in.co.dhdigital.processors.StatementCacheProcessor;
import in.co.dhdigital.processors.StatementGetCacheProcessor;

@Component
public class StatementRoute extends RouteBuilder{

	@Override
	public void configure() throws Exception {
		// TODO Auto-generated method stub
		
		AggregationStrategy aggregationStrategy = new AccountDetailsAggregator();

		JacksonDataFormat jsonDataFormat = new JacksonDataFormat(RestartResponse.class);
			
	      restConfiguration().component("jetty")
	      .bindingMode(RestBindingMode.json)
	      .dataFormatProperty("prettyPrint", "true")
	      .port(8081);
	      
	      rest("api/v1/statement").consumes("application/json").produces("application/json")
	      .get("/{accountnumber}").outType(StatementServiceResponse.class)
	      .to("direct:cache");
	      
	      from("direct:cache")
	     // .process("statementGetCacheProcessor")
	      .bean(StatementGetCacheProcessor.class)
	      .choice().when(simple("${header.found} != 'true'"))
	      .to("direct:get-account-details-route")
	      .otherwise()
	      //.process("statementCacheProcessor");
	      .bean(StatementCacheProcessor.class).endChoice();
	      
	      
	      
		from("direct:get-account-details-route")
	      .log("Inside get-account-details-route")
	      .log("Headers: ${headers}")
	      .doTry()
	      .toD("http://{{urls.account-service}}/api/v1/accountdetails/${headers.accountnumber}?bridgeEndpoint=true")
	   //   .unmarshal().custom("convertAccountDetailsResponse")
	   
	      .unmarshal().json(JsonLibrary.Jackson , AccountDetailsResponse.class)
	      .enrich("direct:get-restart", aggregationStrategy)
	      .log("StatementServiceResponse: ${body}")
	      //.process("statementCacheProcessor")
	      .bean(StatementCacheProcessor.class)
	      .doCatch(java.lang.Exception.class)
	      .log("Error while calling Transaction Service: ${body}")
	      .endDoTry().end();
		
//		from("direct:get-restart")
//		.log("Enterd in Restart")
//		.toD("http://{{urls.restart-service}}/api/v1/restart?bridgeEndpoint=true")
//		.unmarshal()
//		.json(JsonLibrary.Jackson,RestartResponse.class)
//		.log("Restart Response ${body}")
//		.end();

		from("direct:get-restart")
		.marshal().json(JsonLibrary.Jackson)
		.toD("http://{{urls.restart-service}}/api/v1/restart?bridgeEndpoint=true")
		.unmarshal().json(JsonLibrary.Jackson, RestartResponse.class)
		//.unmarshal(jsonDataFormat)
		.end().log("Restart Response.. ${body}").end();
	      
	}

}
