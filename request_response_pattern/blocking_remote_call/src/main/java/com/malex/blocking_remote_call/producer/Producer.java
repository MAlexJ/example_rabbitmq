package com.malex.blocking_remote_call.producer;

import com.malex.blocking_remote_call.model.MessageRequest;
import com.malex.blocking_remote_call.model.MessageResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class Producer {

  @Value("${custom.rabbitmq.exchange}")
  public String exchange;

  @Value("${custom.rabbitmq.routing.key}")
  public String routingKey;

  private final RabbitTemplate template;

  public MessageResponse sendEvent(MessageRequest request) {
    log.info(">>> Sending event to queue {}", request);
    MessageResponse response =
        template.convertSendAndReceiveAsType(
            exchange, routingKey, request, new ParameterizedTypeReference<MessageResponse>() {});
    log.info(">>> Received event from queue {}", response);
    return response;
  }
}
