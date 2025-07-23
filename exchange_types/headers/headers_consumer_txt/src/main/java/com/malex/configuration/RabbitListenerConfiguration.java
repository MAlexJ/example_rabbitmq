package com.malex.configuration;

import org.springframework.amqp.core.AcknowledgeMode;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/*
 * SimpleRabbitListenerContainerFactory is the component that configures how your @RabbitListener
 * methods consume messages from RabbitMQ.
 *
 * It tells Spring:
 * - How to connect to RabbitMQ
 * - What message converter to use (e.g., JSON)
 * - How to acknowledge messages
 * - How many messages to prefetch
 * - How to handle errors, concurrency, transactions, etc.
 */
@Configuration
public class RabbitListenerConfiguration {

  @Bean
  public SimpleRabbitListenerContainerFactory rabbitListenerContainerFactory(
      ConnectionFactory connectionFactory, //
      MessageConverter jsonMessageConverter) {
    final var factory = new SimpleRabbitListenerContainerFactory();

    /*
     * Tell the broker how many messages to send to each consumer in a single request.
     * Often this can be set quite high to improve throughput.
     *
     * Request a specific prefetchCount "quality of service" settings for this channel.
     * Note the prefetch count must be between 0 and 65535 (unsigned short in AMQP 0-9-1).
     *
     * Params:  prefetchCount – maximum number of messages that the server will deliver, 0 if unlimited
     *         global – true if the settings should be applied to the entire channel rather than each consumer
     */
    factory.setPrefetchCount(1);

    factory.setConnectionFactory(connectionFactory);
    factory.setMessageConverter(jsonMessageConverter);

    /*
     * The ack knowledge mode to set.
     * Defaults to AcknowledgeMode - AUTO
     *
     * Acknowledge modes:
     * NONE - No acks - autoAck=true in Channel. basicConsume().
     * MANUAL - Manual acks - user must ack/ nack via a channel-aware listener.
     * AUTO - Auto - the container will issue the ack / nack based on whether the listener returns normally,
     *        or throws an exception
     */
    factory.setAcknowledgeMode(AcknowledgeMode.AUTO);
    return factory;
  }
}
