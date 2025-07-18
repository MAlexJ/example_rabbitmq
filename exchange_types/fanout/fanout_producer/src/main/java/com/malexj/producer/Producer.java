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

  /**
   * Creates a News object, stores it in memory, and sends it via RabbitMQ.
   *
   * @param title the news title
   * @param content the news content
   * @return the created News object
   */
  public News publish(String title, String content) {
    var news = createAndSaveNews(title, content);
    rabbitTemplate.convertAndSend(news);
    return news;
  }

  /**
   * Creates a News object with an incremented ID and timestamp, then stores it in memory.
   *
   * @param title the news title
   * @param content the news content
   * @return the created News object
   */
  private News createAndSaveNews(String title, String content) {
    var news = new News(counter.incrementAndGet(), title, content, LocalDateTime.now());
    holder.add(news);
    return news;
  }

  /**
   * Returns all stored news.
   *
   * @return a list of all news
   */
  public List<News> findAll() {
    return holder;
  }
}
