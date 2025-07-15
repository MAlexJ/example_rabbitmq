package com.malexj.configuration.autoconfiguration;

import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.QueueBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/*
 * RabbitMQ producer using a Direct Exchange:
 *
 * 1. Define a Queue
 * 2. Define a DirectExchange
 * 4. Bind the Queue to the Exchange using a routingKey
 *
 * Note:
 * When a message is published to a direct exchange, it must be routed to at least one queue (matched via a binding-key),
 * otherwise it's dropped (unless configured with a dead-letter or alternate exchange).
 */
@Configuration
public class QueueConfiguration {

  @Value("${custom.rabbitmq.queue}")
  private String queue;

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
    return QueueBuilder.durable(queue)
        /*
         * 1 hour = 60 minutes * 60 seconds * 1000 ms = 3,600,000 ms
         */
        .ttl(3600000) // 1 hour in milliseconds
        .build();
  }
}
