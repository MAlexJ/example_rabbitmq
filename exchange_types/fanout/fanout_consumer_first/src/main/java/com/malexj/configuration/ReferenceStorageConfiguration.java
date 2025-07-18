package com.malexj.configuration;

import com.malexj.consumer.News;
import java.util.concurrent.atomic.AtomicReference;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ReferenceStorageConfiguration {

  @Bean
  public AtomicReference<News> referenceStorage() {
    return new AtomicReference<>();
  }
}
