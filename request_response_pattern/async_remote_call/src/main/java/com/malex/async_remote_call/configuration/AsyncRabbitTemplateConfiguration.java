package com.malex.async_remote_call.configuration;

import org.springframework.amqp.rabbit.AsyncRabbitTemplate;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableAutoConfiguration
public class AsyncRabbitTemplateConfiguration {

  @Bean
  public AsyncRabbitTemplate asyncRabbitTemplate(RabbitTemplate rabbitTemplate) {
    return new AsyncRabbitTemplate(rabbitTemplate);
  }
}
