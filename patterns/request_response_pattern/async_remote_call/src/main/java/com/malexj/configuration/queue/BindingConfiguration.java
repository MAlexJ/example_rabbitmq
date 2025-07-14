package com.malexj.configuration.queue;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BindingConfiguration {
  /**
   * The routing key used for binding the queue to the exchange, loaded from application properties.
   */
  @Value("${custom.rabbitmq.routing.key}")
  private String routingKey;

  /**
   * Defines and registers a binding between the queue and exchange using a routing key.
   *
   * @param queue the {@link Queue} to be bound.
   * @param exchange the {@link DirectExchange} to bind the queue to.
   * @return a {@link Binding} instance linking the queue and exchange.
   */
  @Bean
  public Binding binding(Queue queue, DirectExchange exchange) {
    return BindingBuilder.bind(queue).to(exchange).with(routingKey);
  }
}
