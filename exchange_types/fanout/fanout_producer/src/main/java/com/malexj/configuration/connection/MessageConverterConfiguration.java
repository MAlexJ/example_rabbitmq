package com.malexj.configuration.connection;

import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/*
 * To customize how messages are serialized and deserialized between your Java application
 * and RabbitMQ, specifically using JSON format.
 */
@Configuration
public class MessageConverterConfiguration {

  /*
   * RabbitMQ works with byte[] payloads for messages.
   * Spring AMQP supports different MessageConverter implementations to convert those payloads
   * into Java objects and vice versa.
   * One of the most common is Jackson2JsonMessageConverter,
   * which converts Java objects to/from JSON using Jackson (ObjectMapper).
   */
  @Bean
  public MessageConverter jsonMessageConverter() {
    return new Jackson2JsonMessageConverter();
  }
}
