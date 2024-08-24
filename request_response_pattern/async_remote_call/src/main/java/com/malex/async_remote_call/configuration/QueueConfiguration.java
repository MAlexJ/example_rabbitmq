package com.malex.async_remote_call.configuration;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.ExchangeBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.QueueBuilder;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class QueueConfiguration {
  @Value("${custom.rabbitmq.queue.request}")
  private String queue;

  @Value("${custom.rabbitmq.exchange}")
  private String exchange;

  @Value("${custom.rabbitmq.routing.key}")
  private String routingKey;

  @Bean
  @Qualifier("rabbitmq.queue")
  public Queue queue() {
    return QueueBuilder.durable(queue).build();
  }

  @Bean
  @Qualifier("rabbitmq.exchange")
  public DirectExchange exchange() {
    return ExchangeBuilder.directExchange(exchange).build();
  }

  @Bean
  public Binding binding(
      @Qualifier("rabbitmq.queue") Queue queue,
      @Qualifier("rabbitmq.exchange") DirectExchange exchange) {
    return BindingBuilder.bind(queue).to(exchange).with(routingKey);
  }
}
