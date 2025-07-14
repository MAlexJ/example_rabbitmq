package com.malexj.configuration;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.ExchangeBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.QueueBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/** Configuration class for setting up RabbitMQ queue, exchange, and binding. */
@Configuration
public class QueueConfiguration {

  /** The name of the queue, loaded from application properties. */
  @Value("${custom.rabbitmq.queue.request}")
  private String queue;

  /** The name of the exchange, loaded from application properties. */
  @Value("${custom.rabbitmq.exchange}")
  private String exchange;

  /**
   * The routing key used for binding the queue to the exchange, loaded from application properties.
   */
  @Value("${custom.rabbitmq.routing.key}")
  private String routingKey;

  /**
   * Defines and registers a durable RabbitMQ queue.
   *
   * @return a durable {@link Queue} instance.
   */
  @Bean
  public Queue queue() {
    return QueueBuilder.durable(queue).build();
  }

  /**
   * Defines and registers a direct exchange.
   *
   * @return a {@link DirectExchange} instance.
   */
  @Bean
  public DirectExchange exchange() {
    return ExchangeBuilder.directExchange(exchange).build();
  }

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
