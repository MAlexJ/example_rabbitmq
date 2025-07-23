package com.malexj.producer;

import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class Producer {

  private final RabbitTemplate rabbitTemplate;

  public void publish(Document document) {
    /*
     * Note:
     * 1. Exchange configuration in TemplateConfiguration.class >> rabbitTemplate.setExchange(exchange)
     * 2. Routing key is ignored or empty
     */
    rabbitTemplate.convertAndSend(
        document,
        message -> {
          message.getMessageProperties().setHeader("content", "json");
          message.getMessageProperties().setHeader("format", document.type().abbreviation());
          return message;
        });
  }
}
