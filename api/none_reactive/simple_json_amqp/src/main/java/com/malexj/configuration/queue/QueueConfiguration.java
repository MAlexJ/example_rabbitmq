package com.malexj.configuration.queue;

import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.QueueBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class QueueConfiguration {

  @Value("${custom.rabbitmq.queue.request}")
  private String queue;

  @Value("${custom.rabbitmq.exchange}")
  private String exchange;

  @Bean
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
}
