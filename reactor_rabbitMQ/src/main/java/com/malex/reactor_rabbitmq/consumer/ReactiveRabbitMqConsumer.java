package com.malex.reactor_rabbitmq.consumer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.malex.reactor_rabbitmq.event.MessageEvent;
import com.rabbitmq.client.AMQP;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.utils.SerializationUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import reactor.core.Disposable;
import reactor.rabbitmq.Receiver;

@Slf4j
@Component
@RequiredArgsConstructor
public class ReactiveRabbitMqConsumer {

  private final Receiver receiver;
  private final ObjectMapper jsonMapper;

  @Value("${custom.rabbitmq.queue}")
  private String queue;

  @PostConstruct
  private void init() {
    consume();
  }

  /*
   * Consume messages from the queue
   */
  public Disposable consume() {

    return receiver
        .consumeAutoAck(queue)
        .subscribe(
            delivery -> {
              AMQP.BasicProperties properties = delivery.getProperties();
              byte[] body = delivery.getBody();

              // 1. Deserialize byte to json
              String json = (String) SerializationUtils.deserialize(body);

              // 2. map json to Order object
              try {
                MessageEvent event = jsonMapper.readValue(json, MessageEvent.class);
                log.info(" <<<< properties - {}", properties);
                log.info(" <<<< json - {}", json);
                log.info(" <<<< event - {}", event);

                /*
                    Business logic
                */

              } catch (JsonProcessingException e) {
                e.printStackTrace();
              }
            });
  }
}
