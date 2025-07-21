package com.malex.bean_configuration_perfect_count.configuration.autoconfiguration;

import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.ExchangeBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DirectExchangeConfiguration {

  @Value("${custom.rabbitmq.exchange}")
  private String exchange;

  @Bean
  public DirectExchange directExchange() {
    return ExchangeBuilder.directExchange(exchange).build();
  }
}
