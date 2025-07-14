package com.malexj.configuration;

import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.QueueBuilder;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Configuration class for setting up a reply queue in RabbitMQ.
 *
 * <p>This class:
 *
 * <ul>
 *   <li>Defines a reply queue.
 *   <li>Creates and configures the reply queue.
 *   <li>Sets up a listener container for handling reply messages.
 * </ul>
 */
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

  /**
   * Configures a listener container for handling messages from the reply queue.
   *
   * @param connectionFactory the RabbitMQ connection factory
   * @param rabbitTemplate the RabbitMQ template for message processing
   * @return a {@link SimpleMessageListenerContainer} instance
   */
  @Bean
  public SimpleMessageListenerContainer replyListenerContainer(
      ConnectionFactory connectionFactory, RabbitTemplate rabbitTemplate) {
    var container = new SimpleMessageListenerContainer(connectionFactory);
    container.setQueues(replyQueue());
    container.setMessageListener(rabbitTemplate);
    return container;
  }
}
