package com.malexj.rest;

import com.malexj.producer.Producer;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/v1/events")
@RestController
@RequiredArgsConstructor
public class EventRestController {

  private final Producer producer;

  @GetMapping
  public ResponseEntity<List<EventResponse>> find() {
    var events =
        producer.findAll().stream()
            .map(e -> new EventResponse(e.id(), e.message(), e.timestamp()))
            .toList();
    return ResponseEntity.ok(events);
  }

  @PostMapping
  public ResponseEntity<EventResponse> send(@RequestBody EventRequest request) {
    var event = producer.publish(request.message());
    var response = new EventResponse(event.id(), event.message(), event.timestamp());
    return ResponseEntity.ok(response);
  }
}
