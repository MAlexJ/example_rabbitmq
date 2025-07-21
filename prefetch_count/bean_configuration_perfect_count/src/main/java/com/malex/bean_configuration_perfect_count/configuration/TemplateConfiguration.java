package com.malex.bean_configuration_perfect_count.configuration;

import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@EnableRabbit
@Configuration
public class TemplateConfiguration {

  @Value("${custom.rabbitmq.exchange}")
  private String exchange;

  @Value("${custom.rabbitmq.routing.key}")
  private String routingKey;

  @Bean
  public RabbitTemplate rabbitTemplate(
      ConnectionFactory connectionFactory, MessageConverter jsonMessageConverter) {
    final var template = new RabbitTemplate(connectionFactory);
    template.setMessageConverter(jsonMessageConverter);
    template.setExchange(exchange);
    template.setRoutingKey(routingKey);
    return template;
  }
}
