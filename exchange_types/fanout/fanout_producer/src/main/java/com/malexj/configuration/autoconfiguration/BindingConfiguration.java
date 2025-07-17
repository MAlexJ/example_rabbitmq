package com.malexj.configuration.autoconfiguration;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/*
 * To bind a FanoutExchange in Spring Boot, you don't need a routing key — unlike a DirectExchange,
 * the fanout exchange ignores routing keys and broadcasts messages to all bound queues.* otherwise
 * it's dropped (unless configured with a dead-letter or alternate exchange).
 *
 * Summary:
 * - FanoutExchange: Broadcasts to all bound queues
 * - No need to specify or use a routing key
 * - Each queue gets a copy of the message
 */
@Configuration
public class BindingConfiguration {

  @Bean
  public Binding bindingFirst(Queue firstQueue, FanoutExchange fanoutExchange) {
    return BindingBuilder.bind(firstQueue).to(fanoutExchange);
  }

  @Bean
  public Binding bindingSecond(Queue secondQueue, FanoutExchange fanoutExchange) {
    return BindingBuilder.bind(secondQueue).to(fanoutExchange);
  }
}
