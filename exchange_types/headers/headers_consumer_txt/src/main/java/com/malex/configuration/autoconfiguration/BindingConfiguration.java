package com.malex.configuration.autoconfiguration;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.HeadersExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BindingConfiguration {

  @Value("${custom.rabbitmq.headers.name}")
  private String headerName;

  @Value("${custom.rabbitmq.headers.type}")
  private String type;

  @Bean
  public Binding pdfBinding(Queue pdfQueue, HeadersExchange headersExchange) {
    return BindingBuilder.bind(pdfQueue)
        .to(headersExchange)
        // .whereAny(....)
        //  .whereAll(...)
        .where(headerName)
        // .exists()
        .matches(type); // Minimal header match
  }
}
