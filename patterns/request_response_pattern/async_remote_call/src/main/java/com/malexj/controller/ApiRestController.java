package com.malexj.controller;

import com.malexj.model.MessageRequest;
import com.malexj.model.MessageResponse;
import com.malexj.producer.Producer;
import java.util.concurrent.CompletableFuture;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.RabbitConverterFuture;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/messages")
@RequiredArgsConstructor
public class ApiRestController {

  private final Producer producer;

  @PostMapping
  public CompletableFuture<ResponseEntity<MessageResponse>> sendMessage(
      @RequestBody MessageRequest request) {
    RabbitConverterFuture<MessageResponse> future = producer.sendWithFuture(request);
    return future.thenApplyAsync(r -> ResponseEntity.ok().body(r));
  }
}
