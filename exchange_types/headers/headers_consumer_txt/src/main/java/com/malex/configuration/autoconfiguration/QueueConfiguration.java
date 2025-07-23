package com.malex.configuration.autoconfiguration;

import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.QueueBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class QueueConfiguration {

  @Value("${custom.rabbitmq.queue}")
  private String name;

  @Bean
  public Queue queue() {
    return QueueBuilder.durable(name)
        /*
         * 1 hour = 60 minutes * 60 seconds * 1000 ms = 3,600,000 ms
         */
        .ttl(3600000) // 1 hour in milliseconds
        .build();
  }
}
