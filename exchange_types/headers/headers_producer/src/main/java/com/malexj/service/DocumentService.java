package com.malexj.service;

import com.malexj.producer.Document;
import com.malexj.producer.DocumentType;
import com.malexj.producer.Producer;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DocumentService {

  private final Producer producer;
  private final List<Document> storage;

  public Document publish(String name, String type) {
    var document =
        new Document(
            UUID.randomUUID().toString(), name, DocumentType.findByName(type), LocalDateTime.now());

    producer.publish(document);
    storage.add(document);
    return document;
  }

  public List<Document> findAll() {
    return storage;
  }
}
