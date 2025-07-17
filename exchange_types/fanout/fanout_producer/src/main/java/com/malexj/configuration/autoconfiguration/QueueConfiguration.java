package com.malexj.configuration.autoconfiguration;

import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.QueueBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/*
 * RabbitMQ producer using a Fanout Exchange:
 **/
@Configuration
public class QueueConfiguration {

  @Value("${custom.rabbitmq.queue.first}")
  private String firstQueue;

  @Value("${custom.rabbitmq.queue.second}")
  private String secondQueue;

  @Bean
  public Queue firstQueue() {
    /*
     * Time-To-Live Feature
     *
     * With RabbitMQ, you can set a TTL (time-to-live) argument or policy for messages and queues.
     * As the name suggests, TTL specifies the time period that the messages and queues "live for".
     *
     * A message that has been in the queue for longer than the configured TTL is said to be expired.
     */
    return QueueBuilder.durable(firstQueue)
        /*
         * 1 hour = 60 minutes * 60 seconds * 1000 ms = 3,600,000 ms
         */
        .ttl(3600000) // 1 hour in milliseconds
        .build();
  }

  @Bean
  public Queue secondQueue() {
    /*
     * Time-To-Live Feature
     *
     * With RabbitMQ, you can set a TTL (time-to-live) argument or policy for messages and queues.
     * As the name suggests, TTL specifies the time period that the messages and queues "live for".
     *
     * A message that has been in the queue for longer than the configured TTL is said to be expired.
     */
    return QueueBuilder.durable(secondQueue)
        /*
         * 1 hour = 60 minutes * 60 seconds * 1000 ms = 3,600,000 ms
         */
        .ttl(3600000) // 1 hour in milliseconds
        .build();
  }
}
