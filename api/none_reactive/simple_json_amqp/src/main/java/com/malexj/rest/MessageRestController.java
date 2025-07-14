package com.malexj.rest;

import com.malexj.producer.Producer;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/messages")
@RequiredArgsConstructor
public class MessageRestController {

  public final Producer producer;

  @PostMapping
  public ResponseEntity<Void> sendMessage(@RequestBody MessageEvent event) {
    producer.sendEvent(event);
    return ResponseEntity.ok().build();
  }
}
