package com.malex.async_remote_call.producer;

import com.malex.async_remote_call.model.MessageRequest;
import com.malex.async_remote_call.model.MessageResponse;
import java.util.concurrent.TimeUnit;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.MessageDeliveryMode;
import org.springframework.amqp.rabbit.AsyncRabbitTemplate;
import org.springframework.amqp.rabbit.RabbitConverterFuture;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class Producer {

  private final AsyncRabbitTemplate asyncRabbitTemplate;

  @Value("${custom.rabbitmq.exchange}")
  public String exchange;

  @Value("${custom.rabbitmq.routing.key}")
  public String routingKey;

  /*
   * Async Rabbit Template:
   * https://docs.spring.io/spring-amqp/reference/amqp/request-reply.html
   */
  public RabbitConverterFuture<MessageResponse> sendWithFuture(MessageRequest request) {
    RabbitConverterFuture<MessageResponse> future =
        asyncRabbitTemplate.convertSendAndReceiveAsType(
            exchange,
            routingKey,
            request,
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
              String correlationId = properties.getCorrelationId();
              log.info(">>> correlationId - {}", correlationId);
              return message;
            },
            new ParameterizedTypeReference<>() {});
    return future;
  }
}
