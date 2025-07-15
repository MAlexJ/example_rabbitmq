package com.malexj.consumer;

import com.malexj.websocket.EventWebSocketHandler;
import com.rabbitmq.client.Channel;
import java.util.concurrent.atomic.AtomicReference;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.support.AmqpHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RabbitListener(queues = "${custom.rabbitmq.queue}")
@RequiredArgsConstructor
public class Consumer {

  private final AtomicReference<Event> eventReferenceStorage;
  private final EventWebSocketHandler eventWebSocketHandler;

  @RabbitHandler
  public void handle(Event event, Channel channel, @Header(AmqpHeaders.DELIVERY_TAG) long tag) {
    log.info("  <<< Received message: {}", event);
    log.info("  <<< Delivery tag: {}", tag);
    log.info("  <<< Channel channel: {}", channel);

    // Update the event reference with the latest message
    eventReferenceStorage.set(event);

    // Send the event to connected WebSocket clients
    eventWebSocketHandler.publishEvent(event);
  }
}
