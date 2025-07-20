package com.malexj.rest;

import com.malexj.producer.Producer;
import java.time.LocalDateTime;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/events")
@RequiredArgsConstructor
public class EventController {

  private final Producer producer;

  @PostMapping
  public ResponseEntity<EventResponse> post(@RequestBody EventRequest request) {
    var event = producer.sendEvent(request.text());
    return ResponseEntity.ok(new EventResponse(event));
  }

  public record EventRequest(String text) {
    public EventRequest {
      Objects.requireNonNull(text, "the 'text' field is required");
    }
  }

  public record EventResponse(String id, String text, LocalDateTime timestamp) {
    public EventResponse(Producer.Event event) {
      this(event.id(), event.text(), event.timestamp());
    }
  }
}
