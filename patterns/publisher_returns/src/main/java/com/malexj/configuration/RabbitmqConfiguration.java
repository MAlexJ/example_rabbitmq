package com.malexj.configuration;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Slf4j
@Configuration
public class RabbitmqConfiguration {

  @Value("${custom.rabbitmq_exchange}")
  private String exchange;

  @Bean
  public TopicExchange testExchange() {
    return new TopicExchange(exchange, false, false);
  }

  @Bean
  public RabbitTemplate rabbitTemplate(
      ConnectionFactory factory, MessageConverter messageConverter) {
    RabbitTemplate template = new RabbitTemplate(factory);
    template.setExchange(exchange);
    template.setMessageConverter(messageConverter);
    template.setMandatory(true);
    template.setReturnsCallback(
        returned -> {
          log.error("Returned message body: {}", new String(returned.getMessage().getBody()));
          log.error("Reply code: {}", returned.getReplyCode());
          log.error("Reply text: {}", returned.getReplyText());
          log.error("Exchange: {}", returned.getExchange());
          log.error("Routing key: {}", returned.getRoutingKey());
        });
    return template;
  }

  @Bean
  public MessageConverter messageConverter() {
    return new Jackson2JsonMessageConverter();
  }
}
