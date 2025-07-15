package com.malexj.configuration;

import com.malexj.producer.Event;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class InMemoryHolderConfiguration {

  @Bean
  public List<Event> holder() {
    return new CopyOnWriteArrayList<>();
  }
}
