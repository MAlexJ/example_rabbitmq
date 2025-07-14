package com.malexj.configuration;

import org.springframework.amqp.rabbit.AsyncRabbitTemplate;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Configuration class for setting up RabbitMQ asynchronous messaging.
 */
@Configuration
public class AsyncRabbitTemplateConfiguration {

  /**
   * Creates an {@link AsyncRabbitTemplate} bean to enable asynchronous communication with RabbitMQ.
   *
   * @param rabbitTemplate the {@link RabbitTemplate} instance used for message processing.
   * @return an instance of {@link AsyncRabbitTemplate}.
   */
  @Bean
  public AsyncRabbitTemplate asyncRabbitTemplate(RabbitTemplate rabbitTemplate) {
    return new AsyncRabbitTemplate(rabbitTemplate);
  }

  @Value("${custom.rabbitmq.queue.reply}")
  private String replyQueue;

  /**
   * Configures and creates a {@link RabbitTemplate} bean.
   *
   * @param connectionFactory the RabbitMQ connection factory.
   * @param jsonMessageConverter the message converter for handling JSON messages.
   * @return a configured instance of {@link RabbitTemplate}.
   */
  @Bean
  public RabbitTemplate rabbitTemplate(
          ConnectionFactory connectionFactory, MessageConverter jsonMessageConverter) {
    final RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
    rabbitTemplate.setMessageConverter(jsonMessageConverter);

    /*
     * Configures the reply queue and timeout settings for handling request-reply messaging.
     */
    rabbitTemplate.setReplyAddress(replyQueue);
    rabbitTemplate.setReplyTimeout(5000);

    return rabbitTemplate;
  }
}
