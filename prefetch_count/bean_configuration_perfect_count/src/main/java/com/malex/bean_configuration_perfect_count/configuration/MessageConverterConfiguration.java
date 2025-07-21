package com.malex.bean_configuration_perfect_count.configuration;

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
   * Spring AMQP supports different MessageConverter implementations to convert those payloads into Java objects.
   * One of the most common is Jackson2JsonMessageConverter, which converts Java objects to/from JSON
   * using Jackson (ObjectMapper).
   *
   * Note: new Jackson2JsonMessageConverter()
   * Spring will use the default ObjectMapper from Jackson, which works fine for most applications.
   */
  @Bean
  public MessageConverter jsonMessageConverter() {
    // If you're fine with default Jackson settings (no customizations).
    return new Jackson2JsonMessageConverter();
  }

}
