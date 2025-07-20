package com.malexj.producer;

import java.time.LocalDateTime;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/*
 * Service responsible for sending messages to RabbitMQ using the configured {@link RabbitTemplate}.
 *
 * Each message is sent with a unique {@link CorrelationData} ID, enabling tracking of message
 * delivery status via publisher confirms.
 *
 * Usage: Inject this service and call {@code sendMessage()} specifying the
 * target exchange, routing key, and message payload.
 *
 * Ensure that the RabbitTemplate has confirmed and returns callbacks configured
 * to handle delivery outcomes effectively.
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class Producer {

  /*
   * The RabbitTemplate instance responsible for publishing messages to RabbitMQ.
   * It should be configured with mandatory publishing and callbacks.
   */
  private final RabbitTemplate rabbitTemplate;

  @Value("${custom.rabbitmq.routing_key}")
  private String routingKey;

  /**
   * Sends an event message to the specified RabbitMQ exchange using the given routing key.
   *
   * @param text the test payload for event
   */
  public Event sendEvent(String text) {
    var uuid = UUID.randomUUID().toString();
    var event = new Event(uuid, text, LocalDateTime.now());
    var correlationData = new CorrelationData(uuid);

    try {
      rabbitTemplate.convertAndSend(routingKey, event, correlationData);
      log.debug(
          "Message event to routingKey={}, correlationId={}, payload={}",
          routingKey,
          correlationData.getId(),
          event);
    } catch (Exception ex) {
      log.error("Failed to send event routingKey={}, payload={}", routingKey, event, ex);
      throw ex; // or handle failure (e.g., DLQ, retry)
    }

    return event;
  }

  public record Event(String id, String text, LocalDateTime timestamp) {}
}
