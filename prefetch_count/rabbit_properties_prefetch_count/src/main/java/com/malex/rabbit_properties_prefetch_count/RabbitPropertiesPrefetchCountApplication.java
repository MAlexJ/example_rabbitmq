package com.malex.rabbit_properties_prefetch_count;

import java.util.Objects;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@SpringBootApplication
public class RabbitPropertiesPrefetchCountApplication {

  public static void main(String[] args) {
    SpringApplication.run(RabbitPropertiesPrefetchCountApplication.class, args);
  }

  record MessageEvent(Integer id, String text) {
    public MessageEvent {
      Objects.requireNonNull(id, "id cannot be null");
      Objects.requireNonNull(text, "text cannot be null");
    }
  }

  @RestController
  @RequiredArgsConstructor
  class ApiRestController {

    private final Producer producer;

    @PostMapping("/v1/messages")
    public ResponseEntity<MessageEvent> post(@RequestBody MessageEvent event) {
      producer.publish(event);
      return ResponseEntity.status(HttpStatus.CREATED).body(event);
    }
  }

  @Component
  @RequiredArgsConstructor
  class Producer {

    private final RabbitTemplate rabbitTemplate;

    public void publish(MessageEvent event) {
      log.info("Published message: {}", event);
      /*
       * Basic RPC pattern with conversion.
       *
       * Send a Java object converted to a message to a specific exchange with a specific routing key
       * and attempt to receive a response, converting that to a Java object.
       * Implementations will normally set the reply-to header to an exclusive queue
       * and wait up for some time limited by a timeout.
       */
      rabbitTemplate.convertAndSend(event);
    }
  }
}
