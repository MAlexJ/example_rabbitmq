package com.malexj.configuration;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/*
 * Configuration class responsible for creating and configuring a {@link RabbitTemplate} bean used
 * to send messages to RabbitMQ.
 *
 * The template is customized to handle publisher confirms and returned messages.
 *
 * Key configurations:
 *
 * - Mandatory flag: Ensures messages that cannot be routed are returned to the producer.
 * - Confirm Callback: Logs acknowledgments (ACK/NACK) from the broker when messages
 *   are published in "publisher confirm" mode
 * - Return Callback: Handles unroutable messages by logging them when returned by the broker.
 *
 *
 * Ensure that publisher confirms and returns are enabled in your RabbitMQ connection factory for
 * this configuration to be effective.
 */
@Slf4j
@Configuration
public class TemplateConfiguration {

  @Value("${custom.rabbitmq.exchange}")
  private String exchange;

  /*
   * Defines a singleton {@link RabbitTemplate} bean configured for mandatory publishing, confirm
   * callbacks, and returns callbacks.
   */
  @Bean
  public RabbitTemplate rabbitTemplate(
      ConnectionFactory connectionFactory, MessageConverter jsonMessageConverter) {
    var template = new RabbitTemplate(connectionFactory);

    // set JSON message converter
    template.setMessageConverter(jsonMessageConverter);

    // set exchange
    template.setExchange(exchange);

    // Enables returns for unroutable messages (when no queue is matched).
    template.setMandatory(true);

    // Callback executed when broker confirms (ACK/NACK) a published message.
    template.setConfirmCallback(
        (correlationData, ack, cause) -> {
          if (ack) {
            log.info("Confirmed: {}", correlationData != null ? correlationData.getId() : "null");
          } else {
            log.error(
                "NACK: {}, Cause: {}",
                correlationData != null ? correlationData.getId() : "null",
                cause);
          }
        });

    // Callback triggered when a message is returned (unroutable).
    template.setReturnsCallback(
        returned ->
            log.warn(
                "Returned message: {}, Reason: {}, RoutingKey: {}",
                returned.getMessage(),
                returned.getReplyText(),
                returned.getRoutingKey()));

    return template;
  }
}
