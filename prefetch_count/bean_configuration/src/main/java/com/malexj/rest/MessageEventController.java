package com.malexj.rest;

import com.malexj.event.MessageEvent;
import com.malexj.producer.Producer;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/v1/messages")
@RestController
@RequiredArgsConstructor
public class MessageEventController {

  private final Producer producer;

  @PostMapping
  public ResponseEntity<MessageEvent> post(@RequestBody MessageEvent event) {
    producer.publish(event);
    return ResponseEntity.status(HttpStatus.CREATED).body(event);
  }
}
