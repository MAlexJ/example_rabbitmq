package com.malex.blocking_remote_call.controller;

import com.malex.blocking_remote_call.model.MessageRequest;
import com.malex.blocking_remote_call.model.MessageResponse;
import com.malex.blocking_remote_call.producer.Producer;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/messages")
@RequiredArgsConstructor
public class RestApiController {

  public final Producer producer;

  @PostMapping
  public ResponseEntity<MessageResponse> sendMessage(@RequestBody MessageRequest request) {
    return ResponseEntity.ok(producer.sendEvent(request));
  }
}
