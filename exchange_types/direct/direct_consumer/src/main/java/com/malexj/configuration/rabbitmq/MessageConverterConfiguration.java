package com.malexj.configuration.rabbitmq;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.malexj.consumer.Event;
import java.util.HashMap;
import java.util.Map;
import org.springframework.amqp.support.converter.DefaultJackson2JavaTypeMapper;
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
   *  RabbitMQ works with byte[] payloads for messages.
   * Spring AMQP supports different MessageConverter implementations to convert those payloads
   * into Java objects and vice versa.
   * One of the most common is Jackson2JsonMessageConverter,
   * which converts Java objects to/from JSON using Jackson (ObjectMapper).
   *
   * Option 1: Share a common model module
   * Move your Event class to a shared module, like:
   *
   * shared-model/
   *   src/main/java/com/malexj/common/Event.java
   */
  @Bean
  public MessageConverter jsonMessageConverter(
      ObjectMapper objectMapper, DefaultJackson2JavaTypeMapper typeMapper) {
    Jackson2JsonMessageConverter converter = new Jackson2JsonMessageConverter(objectMapper);
    converter.setClassMapper(typeMapper);
    return converter;
  }

  /*
   * When using Jackson2JsonMessageConverter, Spring AMQP (and its DefaultJackson2JavaTypeMapper)
   * includes the fully qualified class name in the message header:
   * __TypeId__: com.malexj.producer.Event
   *
   * Option 2: Customize the type mapper on the consumer
   * If you want to decouple the consumer and avoid sharing classes, you can override the Jackson type mapping manually:
   */
  @Bean
  public DefaultJackson2JavaTypeMapper typeMapper() {

    // Map remote type to local type
    var typeMapper = new DefaultJackson2JavaTypeMapper();
    Map<String, Class<?>> idClassMapping = new HashMap<>();
    idClassMapping.put("com.malexj.producer.Event", Event.class);

    typeMapper.setIdClassMapping(idClassMapping);
    return typeMapper;
  }
}
