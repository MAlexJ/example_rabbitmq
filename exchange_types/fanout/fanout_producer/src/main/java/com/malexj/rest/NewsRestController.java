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

@RestController
@RequestMapping("/v1/news")
@RequiredArgsConstructor
public class NewsRestController {

  private final Producer producer;

  @GetMapping
  public ResponseEntity<List<NewsResponse>> findAll() {
    var news =
        producer.findAll().stream()
            .map(n -> new NewsResponse(n.id(), n.content(), n.title(), n.timestamp()))
            .toList();
    return ResponseEntity.ok(news);
  }

  @PostMapping
  public ResponseEntity<NewsResponse> post(@RequestBody NewsRequest request) {
    var news = producer.publish(request.title(), request.content());
    var response = new NewsResponse(news.id(), news.content(), news.title(), news.timestamp());
    return ResponseEntity.ok(response);
  }
}
