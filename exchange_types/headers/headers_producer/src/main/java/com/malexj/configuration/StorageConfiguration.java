package com.malexj.configuration;

import com.malexj.producer.Event;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicReference;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class StorageConfiguration {

  @Bean
  public AtomicReference<Event> referenceStorage() {
    return new AtomicReference<>();
  }

  @Bean
  public List<Event> eventStorage() {
    return new CopyOnWriteArrayList<>();
  }
}
