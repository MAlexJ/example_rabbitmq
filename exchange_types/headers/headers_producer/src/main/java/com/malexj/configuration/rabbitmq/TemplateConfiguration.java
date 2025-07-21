package com.malexj.configuration.rabbitmq;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Slf4j
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
    rabbitTemplate.setMandatory(true);
    rabbitTemplate.setReturnsCallback(
        returned -> {
          log.error(">>> Returned message body: {}", new String(returned.getMessage().getBody()));
          log.error(">>> Reply code: {}", returned.getReplyCode());
          log.error(">>> Reply text: {}", returned.getReplyText());
          log.error(">>> Exchange: {}", returned.getExchange());
          log.error(">>> Routing key: {}", returned.getRoutingKey());
        });
    return rabbitTemplate;
  }
}
