package com.malexj.producer;

import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class Producer {

  private final RabbitTemplate rabbitTemplate;
  private final AtomicInteger counter;
  private final List<Event> holder;

  public Event publish(String message) {
    var event = new Event(counter.incrementAndGet(), message, LocalDateTime.now());
    holder.add(event);
    rabbitTemplate.convertAndSend(event);
    return event;
  }

  public List<Event> findAll() {
    return holder;
  }
}
