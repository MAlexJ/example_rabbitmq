package com.malex.bean_configuration_perfect_count.producer;

import com.malex.bean_configuration_perfect_count.event.MessageEvent;
import java.util.concurrent.TimeUnit;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.MessageDeliveryMode;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class Producer {

  /*
   * Configuration the exchange and routing key see: TemplateConfiguration class
   */
  private final RabbitTemplate rabbitTemplate;

  public void publish(MessageEvent event) {
    log.info("Publishing event {}", event);
    rabbitTemplate.convertAndSend(
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
