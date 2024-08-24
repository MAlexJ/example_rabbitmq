package com.malex.bean_configuration_perfect_count.configuration;

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
public class BindingQueueExchangeConfiguration {

  @Value("${custom.rabbitmq.queue}")
  private String queue;

  @Value("${custom.rabbitmq.exchange}")
  private String exchange;

  @Value("${custom.rabbitmq.routing.key}")
  private String routingKey;

  @Bean
  @Qualifier("rabbitmq.queue")
  public Queue queue() {
    /*
     * Time-To-Live Feature
     *
     * With RabbitMQ, you can set a TTL (time-to-live) argument or policy for messages and queues.
     * As the name suggests, TTL specifies the time period that the messages and queues "live for".
     *
     * A message that has been in the queue for longer than the configured TTL is said to be expired.
     */
    return QueueBuilder.durable(queue).ttl(2000).build();
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
