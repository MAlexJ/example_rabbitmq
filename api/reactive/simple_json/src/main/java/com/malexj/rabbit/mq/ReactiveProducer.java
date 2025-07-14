package com.malexj.rabbit.mq;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.malexj.rest.Event;
import com.rabbitmq.client.AMQP;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.utils.SerializationUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.rabbitmq.BindingSpecification;
import reactor.rabbitmq.ExchangeSpecification;
import reactor.rabbitmq.OutboundMessage;
import reactor.rabbitmq.QueueSpecification;
import reactor.rabbitmq.Sender;

@Slf4j
@Component
@RequiredArgsConstructor
public class ReactiveProducer {

  private final ObjectMapper objectMapper;
  private final Sender sender;

  @Value("${custom.rabbitmq.queue}")
  private String queue;

  @Value("${custom.rabbitmq.exchange.name}")
  private String exchange;

  @Value("${custom.rabbitmq.exchange.type}")
  private String type;

  @Value("${custom.rabbitmq.routing.key}")
  private String routingKey;

  public Mono<Event> send(Event event) {
    try {
      // Serialize object to json
      var json = objectMapper.writeValueAsString(event);

      // Serialize JSON to bytes
      byte[] orderSerialized = SerializationUtils.serialize(json);

      // Outbound Message that will be sent by the Sender
      var outboundMessageFlux =
          Flux.just(new OutboundMessage(exchange, routingKey, orderSerialized));

      // Declare the queue then send the flux of messages.
      declareBinding()
          .thenMany(sender.sendWithPublishConfirms(outboundMessageFlux))
          .doOnError(e -> log.error("Send failed", e))
          .subscribe(
              outboundMessageResult -> {
                if (outboundMessageResult.isAck()) {
                  log.info("Event delivered - {}, outboundMessageResult -{}", event);
                }
                log.info("Event sent - {}, outboundMessageResult -{}", event);
                log.info("OutboundMessageResult -{}", outboundMessageResult);
              });
    } catch (JsonProcessingException ex) {
      log.error("Error serializing event", ex);
    }

    // Return a posted object to the client
    return Mono.just(event);
  }

  private Mono<AMQP.Queue.BindOk> declareBinding() {
    return sender
        .declareQueue(QueueSpecification.queue(queue))
        .then(sender.declareExchange(ExchangeSpecification.exchange(exchange).type(type)))
        .then(sender.bind(BindingSpecification.binding(exchange, routingKey, queue)));
  }
}
