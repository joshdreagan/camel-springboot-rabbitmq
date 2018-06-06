package org.apache.camel.examples;

import org.apache.camel.ExchangePattern;
import org.apache.camel.LoggingLevel;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.rest.RestBindingMode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;

@Component
public class CamelConfiguration extends RouteBuilder {

  private static final Logger log = LoggerFactory.getLogger(CamelConfiguration.class);
  
  @Override
  public void configure() throws Exception {
    
    restConfiguration("servlet")
      .bindingMode(RestBindingMode.auto)
      .contextPath("/camel")
      .apiContextPath("/api-doc")
    ;

    rest("/events")
      .post("/")
        .consumes(MediaType.APPLICATION_JSON_VALUE)
        .produces(MediaType.APPLICATION_JSON_VALUE)
        .to("direct:post")
    ;
    
    from("direct:post")
      .routeId("handlePostIncidents")
      .convertBodyTo(String.class)
      .log(LoggingLevel.INFO, log, "Sending Message:\\n${body}")
      .to(ExchangePattern.InOnly, "rabbitmq://{{camel.component.rabbitmq.host}}:{{camel.component.rabbitmq.port}}/events")
      .setBody().constant(null)
    ;
  }
}
