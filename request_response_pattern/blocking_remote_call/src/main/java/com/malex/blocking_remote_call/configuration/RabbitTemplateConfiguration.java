package com.malex.blocking_remote_call.configuration;

import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitTemplateConfiguration {

  @Value("${custom.rabbitmq.queue.reply}")
  private String replyQueue;

  @Bean
  public RabbitTemplate rabbitTemplate(
      ConnectionFactory connectionFactory, MessageConverter jsonMessageConverter) {
    final RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
    rabbitTemplate.setMessageConverter(jsonMessageConverter);

    /*
     * configuration reply queue
     */
    rabbitTemplate.setReplyAddress(replyQueue);
    rabbitTemplate.setReplyTimeout(5000);

    return rabbitTemplate;
  }
}
