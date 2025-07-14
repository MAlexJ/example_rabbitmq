package com.malexj.rest;

import com.malexj.rabbit.mq.ReactiveProducer;
import java.net.URI;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class EventsHandler {

  private final ReactiveProducer producer;

  public Mono<ServerResponse> create(ServerRequest request) {
    return request
        .bodyToMono(Event.class)
        .flatMap(producer::send)
        .flatMap(event -> ServerResponse.created(URI.create("/events/" + event.id())).build());
  }
}
