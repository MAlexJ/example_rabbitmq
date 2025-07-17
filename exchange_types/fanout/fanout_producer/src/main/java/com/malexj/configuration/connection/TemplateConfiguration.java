package com.malexj.configuration.connection;

import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class TemplateConfiguration {

  @Value("${custom.rabbitmq.exchange}")
  private String exchange;

  @Bean
  public RabbitTemplate rabbitTemplate(
      ConnectionFactory connectionFactory, MessageConverter jsonMessageConverter) {
    final var rabbitTemplate = new RabbitTemplate();
    rabbitTemplate.setConnectionFactory(connectionFactory);
    rabbitTemplate.setMessageConverter(jsonMessageConverter);
    rabbitTemplate.setExchange(exchange);
    return rabbitTemplate;
  }
}
