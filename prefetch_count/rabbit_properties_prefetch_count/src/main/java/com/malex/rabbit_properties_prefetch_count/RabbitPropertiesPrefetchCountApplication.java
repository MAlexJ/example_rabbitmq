package com.malex.rabbit_properties_prefetch_count;

import com.rabbitmq.client.Channel;
import java.util.Objects;
import java.util.concurrent.TimeUnit;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.MessageDeliveryMode;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.AmqpHeaders;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.Header;
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

    @Value("${custom.rabbitmq.exchange}")
    public String exchange;

    @Value("${custom.rabbitmq.routing.key}")
    public String routingKey;

    private final RabbitTemplate rabbitTemplate;

    public void publish(MessageEvent event) {
      log.info("Publishing event {}", event);
      rabbitTemplate.convertAndSend(
          exchange,
          routingKey,
          event,
          message -> {
            var properties = message.getMessageProperties();
            log.info("Properties - {}", properties);
            /*
             * Set the message expiration.
             * This is a String property per the AMQP 0.9.1 spec.
             * For RabbitMQ, this is a String representation of the message time to live in milliseconds.
             */
            var oneDayInMillis = Long.toString(TimeUnit.DAYS.toMillis(1));
            properties.setExpiration(oneDayInMillis);

            /*
             * Enumeration for the message delivery mode. Can be persistent or non persistent.
             * Use the method 'toInt' to get the appropriate value that is used by the AMQP protocol instead of the ordinal()
             * value when passing into AMQP APIs.
             *
             * NON_PERSISTENT = 1
             * PERSISTENT = 2
             * default = -1
             */
            properties.setDeliveryMode(MessageDeliveryMode.NON_PERSISTENT);
            return message;
          });
    }
  }

  @Component
  @RabbitListener(queues = "${custom.rabbitmq.queue}")
  class Consumer {

    @RabbitHandler
    public void handle(
        MessageEvent event, Channel channel, @Header(AmqpHeaders.DELIVERY_TAG) long tag) {
      log.info("Received message: {}", event);
      log.info("Delivery tag: {}", tag);
      log.info("Channel channel: {}", channel);
    }
  }
}
