package com.malexj.rabbit.mq;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.malexj.rest.Event;
import com.rabbitmq.client.AMQP;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.utils.SerializationUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import reactor.rabbitmq.Receiver;

@Slf4j
@Component
@RequiredArgsConstructor
public class ReactiveConsumer {

  private final ObjectMapper objectMapper;
  private final Receiver receiver;

  @Value("${custom.rabbitmq.queue}")
  private String queue;

  @PostConstruct
  private void init() {
    consume();
  }

  /*
   * Consume messages from the queue
   */
  public void consume() {
    receiver
        .consumeAutoAck(queue)
        .subscribe(
            delivery -> {
              AMQP.BasicProperties properties = delivery.getProperties();
              byte[] body = delivery.getBody();

              // 1. Deserialize byte to JSON
              String json = (String) SerializationUtils.deserialize(body);

              // 2. map json to Order object
              try {
                Event event = objectMapper.readValue(json, Event.class);
                log.info(" <<<< properties - {}", properties);
                log.info(" <<<< json - {}", json);
                log.info(" <<<< event - {}", event);

                /*
                 *  Business logic
                 */
              } catch (JsonProcessingException e) {
                log.error(e.getMessage(), e);
              }
            });
  }
}
