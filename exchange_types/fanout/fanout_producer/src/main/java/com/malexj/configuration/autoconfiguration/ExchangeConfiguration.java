package com.malexj.configuration.autoconfiguration;

import org.springframework.amqp.core.ExchangeBuilder;
import org.springframework.amqp.core.FanoutExchange;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/*
 * RabbitMQ producer using a Direct Exchange:
 *
 * 1. Define a Queue
 * 2. Define a DirectExchange
 * 4. Bind the Queue to the Exchange using a routingKey
 *
 * Note:
 * When a message is published to a direct exchange, it must be routed to at least one queue (matched via a binding-key),
 * otherwise it's dropped (unless configured with a dead-letter or alternate exchange).
 */
@Configuration
public class ExchangeConfiguration {

  @Value("${custom.rabbitmq.exchange}")
  private String exchange;

  @Bean
  public FanoutExchange exchange() {
    return ExchangeBuilder.fanoutExchange(exchange).build();
  }
}
