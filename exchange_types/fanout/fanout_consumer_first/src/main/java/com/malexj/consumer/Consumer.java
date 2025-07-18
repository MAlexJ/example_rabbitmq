package com.malexj.consumer;

import com.rabbitmq.client.Channel;
import java.util.concurrent.atomic.AtomicReference;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.support.AmqpHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RabbitListener(queues = "${custom.rabbitmq.queue.first}")
@RequiredArgsConstructor
public class Consumer {

  private final AtomicReference<News> referenceStorage;

  @RabbitHandler
  public void handle(News news, Channel channel, @Header(AmqpHeaders.DELIVERY_TAG) long tag) {
    log.info("  <<< Received message: {}", news);
    log.info("  <<< Delivery tag: {}", tag);
    log.info("  <<< Channel channel: {}", channel);

    // Update the event reference with the latest message
    referenceStorage.set(news);
  }
}
