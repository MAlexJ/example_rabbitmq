package com.malexj.configuration.queue;

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

  /**
   * Defines and registers a durable RabbitMQ queue.
   *
   * @return a durable {@link Queue} instance.
   */
  @Bean
  public Queue queue() {
    return QueueBuilder.durable(queue).build();
  }
}
