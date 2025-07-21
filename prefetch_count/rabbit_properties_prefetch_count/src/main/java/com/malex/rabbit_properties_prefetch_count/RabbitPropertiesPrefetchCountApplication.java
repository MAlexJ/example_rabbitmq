package com.malex.rabbit_properties_prefetch_count;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@Slf4j
@SpringBootApplication
public class RabbitPropertiesPrefetchCountApplication {

  public static void main(String[] args) {
    SpringApplication.run(RabbitPropertiesPrefetchCountApplication.class, args);
  }
}
