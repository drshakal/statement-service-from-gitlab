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
//import in.co.dhdigital.processors.StatementCacheProcessor;
//import in.co.dhdigital.processors.StatementGetCacheProcessor;
import in.co.dhdigital.processors.addHeaderProcessor;
import in.co.dhdigital.processors.getHeaderProcessor;

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
	      .port(8081).host("localhost");
	      
	      rest("api/v1/statement").consumes("application/json").produces("application/json")
	      .get("/{accountnumber}").outType(StatementServiceResponse.class)
	      .to("direct:cache02");
	      
//	      from("direct:cache")
//	     // .process("statementGetCacheProcessor")
//	      .bean(StatementGetCacheProcessor.class)
//	      .choice().when(simple("${header.found} != 'true'"))
//	      .to("direct:get-account-details-route")
//	      .otherwise()
//	      //.process("statementCacheProcessor");
//	      .bean(StatementCacheProcessor.class).endChoice();
	      
	      from("direct:cache02")
	      .log("${headers}")
	      .bean(getHeaderProcessor.class)
	      .to("cache://customerInfoCache")
	      .choice().when(simple("${header.CamelCacheElementWasFound} != 'true'"))
	      .to("direct:get-account-details-route")
	      .otherwise()
	      .log("CACHE HIT").endChoice();
	      
	      
//	      .choice().when(header(CacheConstants.CACHE_ELEMENT_WAS_FOUND).isNull()).
//	        // If not found, get the payload and put it to cache
//	        .to("cxf:bean:someHeavyweightOperation").
//	        .setHeader(CacheConstants.CACHE_OPERATION, constant(CacheConstants.CACHE_OPERATION_ADD))
//	        .setHeader(CacheConstants.CACHE_KEY, constant("Ralph_Waldo_Emerson"))
//	        .to("cache://TestCache1")
//	    .end()
	      
	      
	      
//	      <route id="cache_route">
//          <from id="_form12" uri="direct:cachespace"/>
//          <process id="_process2" ref="getHeaderProcessor"/>
//          <log id="_log66" message="::::LOG BEFORE CACHECHECK::::**${headers}****************************************${body}"/>
//          <to id="_to990" uri="cache://customerInfoCache"/>
//          <log id="_log66" message="::::LOG ON CACHECHECK::::**${headers}****************************************${body}"/>
//          <choice id="_choice1">
//              <when id="_when1">
//                  <simple>${header.CamelCacheElementWasFound} == true</simple>
//                  <log id="_log66" message="::::SUCESSSSSSSSSSSSSSS::::**${headers}****************************************${body}"/>
//                  <to id="_to2" uri="mock:out"/>
//              </when>
//              <otherwise id="_otherwise1">
//                  <to id="_to1" uri="direct:getCustomerid"/>
//              </otherwise>
//          </choice>
//          <to id="_to66" uri="mock:out"/>
//      </route>
	      
		from("direct:get-account-details-route")
	      .log("Inside get-account-details-route")
	      .log("Headers: ${headers}")
	      .doTry()
	      .toD("http://{{urls.account-service}}/api/v1/accountdetails/${headers.accountnumber}?bridgeEndpoint=true")
	   //   .unmarshal().custom("convertAccountDetailsResponse")
	   
	      .unmarshal().json(JsonLibrary.Jackson , AccountDetailsResponse.class)
	      .enrich("direct:get-restart", aggregationStrategy)
	      .log("StatementServiceResponse: ${body}")
	      .bean(addHeaderProcessor.class)
	      .to("cache://customerInfoCache")
	      //.process("statementCacheProcessor")
	      //.bean(StatementCacheProcessor.class)
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
