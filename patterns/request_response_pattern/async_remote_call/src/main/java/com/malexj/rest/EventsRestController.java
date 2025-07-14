package com.malexj.controller;

import com.malexj.producer.Producer;
import com.malexj.rest.EventRequest;
import com.malexj.rest.EventResponse;
import java.util.concurrent.CompletableFuture;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.RabbitConverterFuture;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/events")
@RequiredArgsConstructor
public class EventsRestController {

  private final Producer producer;

  @PostMapping
  public CompletableFuture<ResponseEntity<EventResponse>> sendMessage(
      @RequestBody EventRequest request) {

    RabbitConverterFuture<EventResponse> future = producer.sendAsync(request);
    return future.thenApplyAsync(r -> ResponseEntity.ok().body(r));
  }
}
