package com.malexj.configuration;

import com.malexj.consumer.Event;
import java.util.concurrent.atomic.AtomicReference;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ReferenceStorageConfiguration {

  @Bean
  public AtomicReference<Event> eventReferenceStorage() {
    return new AtomicReference<>();
  }
}
