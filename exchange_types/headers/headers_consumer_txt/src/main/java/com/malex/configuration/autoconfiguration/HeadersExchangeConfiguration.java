package com.malex.configuration.autoconfiguration;

import org.springframework.amqp.core.ExchangeBuilder;
import org.springframework.amqp.core.HeadersExchange;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/*
 * Configuration class for declaring a RabbitMQ HeadersExchange bean.
 *
 * Spring AMQP's {@code RabbitAdmin} automatically declares the exchange on the broker at startup
 * if it is present in the context.
 */
@Configuration
public class HeadersExchangeConfiguration {

  @Value("${custom.rabbitmq.exchange}")
  private String exchange;

  /**
   * Declares a {@link HeadersExchange} using the configured exchange name.
   *
   * @return a {@link HeadersExchange} built with {@link ExchangeBuilder}
   */
  @Bean
  public HeadersExchange headersExchange() {
    return ExchangeBuilder.headersExchange(exchange)
        // Makes the exchange durable, meaning it will survive broker restarts.
        .durable(true)
        .build();
  }
}
