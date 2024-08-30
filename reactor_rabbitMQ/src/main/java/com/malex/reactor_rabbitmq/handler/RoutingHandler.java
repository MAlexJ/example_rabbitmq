package com.malex.reactor_rabbitmq.handler;

import com.malex.reactor_rabbitmq.event.MessageEvent;
import com.malex.reactor_rabbitmq.producer.RabbitProducer;
import java.net.URI;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class RoutingHandler {

  private final RabbitProducer producer;

  public Mono<ServerResponse> create(ServerRequest request) {
    return request
        .bodyToMono(MessageEvent.class)
        .flatMap(producer::send)
        .flatMap(event -> ServerResponse.created(URI.create("/events/" + event.id())).build());
  }
}
