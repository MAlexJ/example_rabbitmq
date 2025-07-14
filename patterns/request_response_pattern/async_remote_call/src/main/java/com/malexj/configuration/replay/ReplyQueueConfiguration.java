package com.malexj.configuration.replay;

import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.QueueBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/** Configuration class for setting up a reply queue in RabbitMQ. */
@Configuration
public class ReplyQueueConfiguration {

  /** Name of the reply queue, injected from application properties. */
  @Value("${custom.rabbitmq.queue.reply}")
  private String replyQueue;

  /**
   * Creates a durable reply queue.
   *
   * @return the configured {@link Queue} instance
   */
  @Bean
  public Queue replyQueue() {
    return QueueBuilder.durable(replyQueue).build();
  }
}
