package com.malex.simple_json_amqp.controller;

import com.malex.simple_json_amqp.dto.MessageEvent;
import com.malex.simple_json_amqp.producer.Producer;
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
  public ResponseEntity<Void> sendMessage(@RequestBody MessageEvent event) {
    producer.sendEvent(event);
    return ResponseEntity.ok().build();
  }
}
