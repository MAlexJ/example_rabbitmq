package com.malexj.consumer;

import com.malexj.rest.EventRequest;
import com.malexj.rest.EventResponse;
import com.rabbitmq.client.Channel;
import java.time.LocalDateTime;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.support.AmqpHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RabbitListener(queues = "${custom.rabbitmq.queue.request}")
public class Consumer {

  /*
   * Async Rabbit Template:
   * https://docs.spring.io/spring-amqp/reference/amqp/request-reply.html
   */
  @RabbitHandler
  public EventResponse handle(
      EventRequest event,
      Channel channel,
      Message message,
      @Header(AmqpHeaders.DELIVERY_TAG) long tag) {

    log.info("  <<< Received message: {}", event);
    log.info("  <<< Delivery tag: {}", tag);
    log.info("  <<< Channel channel: {}", channel);

    String correlationId = message.getMessageProperties().getCorrelationId();
    log.info("  <<< Correlation Id: {}", correlationId);

    return new EventResponse(
        "Replay to id: '%s', message:'%s', correlationId: '%s'"
            .formatted(event.id(), event.message(), correlationId),
        LocalDateTime.now());
  }
}
