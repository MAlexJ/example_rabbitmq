package com.malexj.configuration;

import com.malexj.producer.Document;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicReference;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class StorageConfiguration {

  @Bean
  public AtomicReference<Document> referenceStorage() {
    return new AtomicReference<>();
  }

  @Bean
  public List<Document> storage() {
    return new CopyOnWriteArrayList<>();
  }
}
