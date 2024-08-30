package com.malex.reactor_rabbitmq.configuration;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class JsonMapperConfig {

  @Bean
  public ObjectMapper jsonMapper() {
    return new ObjectMapper();
  }
}
