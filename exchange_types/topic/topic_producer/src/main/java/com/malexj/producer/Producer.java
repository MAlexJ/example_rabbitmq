package com.malexj.producer;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class Producer {

  private final RabbitTemplate rabbitTemplate;

  @Value("${custom.rabbitmq.routing_key}")
  private String baseRoutingKey;

  public void publish(Event event) {
    var type = event.type();
    switch (type) {
      case USER -> {
        var usersRoutingKey = baseRoutingKey + ".users";
        log.info("Send message to user with routing key {}", usersRoutingKey);
        rabbitTemplate.convertAndSend(usersRoutingKey, event);
      }
      case ORDER -> {
        var ordersRoutingKey = baseRoutingKey + ".orders";
        log.info("Send message to orders with routing key {}", ordersRoutingKey);
        rabbitTemplate.convertAndSend(ordersRoutingKey, event);
      }
      default -> throw new IllegalArgumentException("Unknown type: %s".formatted(type));
    }
  }
}
