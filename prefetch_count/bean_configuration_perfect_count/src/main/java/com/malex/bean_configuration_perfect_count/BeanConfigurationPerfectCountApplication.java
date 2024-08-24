package com.malex.bean_configuration_perfect_count;

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
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@SpringBootApplication
public class BeanConfigurationPerfectCountApplication {

  public static void main(String[] args) {
    SpringApplication.run(BeanConfigurationPerfectCountApplication.class, args);
  }

  @RestController
  @RequiredArgsConstructor
  class RestApiController {

    private final Producer producer;

    /*
     * rest api doc: https://restfulapi.net/http-methods/
     *
     * rules:
     * 1. Use POST APIs to create new subordinate resources,
     * 2. REST, POST methods are used to create a new resource
     * 3. note that POST is neither safe nor idempotent
     * 4.1. Ideally, if a resource has been created on the origin server,
     *      the response SHOULD be HTTP response code 201 (Created) and contain an entity
     * 4.2. Many times, the action performed by the POST method might not result
     *      in a resource that can be identified by a URI. In this case,
     *       either HTTP response code 200 (OK) or 204 (No Content) is the appropriate response status.
     */

    @PostMapping("/v1/messages")
    public ResponseEntity<MessageEvent> post(@RequestBody MessageEvent event) {
      producer.publish(event);
      return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
  }

  public record MessageEvent(String uuid, String text) {

    public MessageEvent {
      Objects.requireNonNull(uuid, "uuid must not be null");
      Objects.requireNonNull(text, "text must not be null");
    }
  }

  @Service
  @RequiredArgsConstructor
  class Producer {

    @Value("${custom.rabbitmq.exchange}")
    public String exchange;

    @Value("${custom.rabbitmq.routing.key}")
    public String routingKey;

    public final RabbitTemplate rabbitTemplate;

    void publish(MessageEvent event) {
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
            properties.setDeliveryMode(MessageDeliveryMode.PERSISTENT);
            return message;
          });
    }
  }

  @Component
  @RabbitListener(queues = "${custom.rabbitmq.queue}", id = "rabbitmq_consumer")
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
