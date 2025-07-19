package com.malexj.service;

import com.malexj.producer.Event;
import com.malexj.producer.EventType;
import com.malexj.producer.Producer;
import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EventService {

  private final Producer producer;
  private final AtomicInteger counter;
  private final List<Event> eventStorage;
  private final AtomicReference<Event> referenceStorage;

  public List<Event> findAll() {
    return eventStorage;
  }

  public Event publishEvent(String message, String typeName) {
    var type = EventType.findByName(typeName);
    var event = new Event(counter.incrementAndGet(), message, type, LocalDateTime.now());

    // save to a storage
    referenceStorage.set(event);
    eventStorage.add(event);

    // publish to a rabbitmq
    producer.publish(event);

    return event;
  }
}
