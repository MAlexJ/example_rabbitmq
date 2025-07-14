package com.malexj.configuration.queue;

import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.ExchangeBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ExchangeConfiguration {

  @Value("${custom.rabbitmq.exchange}")
  private String exchange;

  @Bean
  public DirectExchange exchange() {
    return ExchangeBuilder.directExchange(exchange).build();
  }
}
