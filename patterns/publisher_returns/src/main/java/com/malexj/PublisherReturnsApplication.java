package com.malexj;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@RequiredArgsConstructor
public class PublisherReturnsApplication {

  private final RabbitTemplate rabbitTemplate;

  @Value("${custom.rabbitmq_exchange}")
  private String exchange;

  @PostConstruct
  public void sendUnroutableMessage() {
    rabbitTemplate.convertAndSend(exchange, "no.queue.key", "hello?");
  }

  public static void main(String[] args) {
    SpringApplication.run(PublisherReturnsApplication.class, args);
  }
}
