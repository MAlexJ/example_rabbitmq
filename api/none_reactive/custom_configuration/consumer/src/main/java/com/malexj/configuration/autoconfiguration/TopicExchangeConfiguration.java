package com.malexj.configuration.autoconfiguration;

import org.springframework.amqp.core.ExchangeBuilder;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/*
 * Configuration class for declaring a RabbitMQ TopicExchange bean.
 *
 * Spring AMQP's {@code RabbitAdmin} automatically declares the exchange on the broker at startup
 * if it is present in the context.
 */
@Configuration
public class TopicExchangeConfiguration {

  @Value("${custom.rabbitmq.exchange}")
  private String exchange;

  /**
   * Declares a {@link TopicExchange} using the configured exchange name.
   *
   * @return a {@link TopicExchange} built with {@link ExchangeBuilder}
   */
  @Bean
  public TopicExchange topicExchange() {
    return ExchangeBuilder.topicExchange(exchange).build();
  }
}
