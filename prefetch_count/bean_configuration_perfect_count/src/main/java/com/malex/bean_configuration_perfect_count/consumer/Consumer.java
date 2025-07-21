package com.malex.bean_configuration_perfect_count.consumer;

import com.malex.bean_configuration_perfect_count.event.MessageEvent;
import com.rabbitmq.client.Channel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.support.AmqpHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RabbitListener(queues = "${custom.rabbitmq.queue}")
public class Consumer {

  @RabbitHandler
  public void handle(
      MessageEvent event, Channel channel, @Header(AmqpHeaders.DELIVERY_TAG) long tag) {
    log.info("Received message: {}", event);
    log.info("Delivery tag: {}", tag);
    log.info("Channel channel: {}", channel);
  }
}
