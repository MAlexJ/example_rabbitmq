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

  private final List<News> holder;
  private final AtomicInteger counter;

  private final RabbitTemplate rabbitTemplate;

  public News publish(String title, String content) {
    var news = new News(counter.incrementAndGet(), title, content, LocalDateTime.now());
    holder.add(news);
    rabbitTemplate.convertAndSend(news);
    return news;
  }

  public List<News> findAll() {
    return holder;
  }
}
