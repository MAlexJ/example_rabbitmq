package com.malexj.rest;

import com.malexj.producer.Event;
import com.malexj.service.EventService;
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
public class EventController {

  private final EventService service;

  @GetMapping
  public ResponseEntity<List<EventResponse>> findAll() {
    var events = service.findAll().stream().map(this::mapToResponse).toList();
    return ResponseEntity.ok(events);
  }

  @PostMapping
  public ResponseEntity<EventResponse> post(@RequestBody EventRequest request) {
    var event = service.publishEvent(request.message(), request.type());
    return ResponseEntity.ok(mapToResponse(event));
  }

  private EventResponse mapToResponse(Event event) {
    return new EventResponse(event.id(), event.message(), event.type().name(), event.timestamp());
  }
}
