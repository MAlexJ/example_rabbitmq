package com.malex.reactor_rabbitmq.producer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.malex.reactor_rabbitmq.event.MessageEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.utils.SerializationUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.rabbitmq.OutboundMessage;
import reactor.rabbitmq.QueueSpecification;
import reactor.rabbitmq.Sender;

@Slf4j
@Component
@RequiredArgsConstructor
public class RabbitProducer {

  @Value("${custom.rabbitmq.queue}")
  private String queue;

  private AmqpTemplate amqpTemplate;

  private final Sender sender;

  public Mono<MessageEvent> send(MessageEvent event) {
    // Map OrderDTO to Order object
    ObjectMapper mapper = new ObjectMapper();
    String json;
    try {
      // Serialize object to json
      json = mapper.writeValueAsString(event);

      // Serialize json to bytes
      byte[] orderSerialized = SerializationUtils.serialize(json);

      // Outbound Message that will be sent by the Sender
      Flux<OutboundMessage> outbound = Flux.just(new OutboundMessage("", queue, orderSerialized));

      // Declare the queue then send the flux of messages.
      sender
          .declareQueue(QueueSpecification.queue(queue))
          .thenMany(sender.sendWithPublishConfirms(outbound))
          .doOnError(e -> log.error("Send failed", e))
          .subscribe(
              m -> {
                log.info("Event sent - {}", event);
              });
    } catch (JsonProcessingException e1) {
      e1.printStackTrace();
    }
    // Return posted object to the client
    return Mono.just(event);
  }
}
