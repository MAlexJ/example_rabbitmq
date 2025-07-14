package com.malexj.producer;

import com.malexj.rest.MessageEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class Producer {

  @Value("${custom.rabbitmq.exchange}")
  public String exchange;

  @Value("${custom.rabbitmq.routing.key}")
  public String routingKey;

  private final RabbitTemplate template;

  public void sendEvent(MessageEvent request) {
    log.info(">>> Sending event to queue {}", request);
    template.convertAndSend(exchange, routingKey, request);
  }
}
