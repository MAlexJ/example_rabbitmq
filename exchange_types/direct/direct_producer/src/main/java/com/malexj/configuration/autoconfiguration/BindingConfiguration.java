package com.malexj.configuration.autoconfiguration;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
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
public class BindingConfiguration {

  @Value("${custom.rabbitmq.routing_key}")
  private String routingKey;

  @Bean
  public Binding binding(Queue queue, DirectExchange exchange) {
    return BindingBuilder.bind(queue).to(exchange).with(routingKey);
  }
}
