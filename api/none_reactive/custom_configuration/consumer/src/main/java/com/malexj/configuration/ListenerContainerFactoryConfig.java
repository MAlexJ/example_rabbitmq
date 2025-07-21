package com.malexj.configuration;

import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ListenerContainerFactoryConfig {

  @Bean
  public SimpleRabbitListenerContainerFactory rabbitListenerContainerFactory(
      ConnectionFactory connectionFactory, MessageConverter jsonMessageConverter) {
    var factory = new SimpleRabbitListenerContainerFactory();
    factory.setConnectionFactory(connectionFactory);

    factory.setMessageConverter(jsonMessageConverter);

    /*
     * Customizing the container for graceful shutdown timeout
     *
     * Key Explanation:
     * setContainerCustomizer() allows you to apply configurations directly to each container that the factory produces.
     * SimpleMessageListenerContainer.setShutdownTimeout(long millis) is the correct place to set the shutdown timeout.
     */
    factory.setContainerCustomizer(
        container ->
            // Best practice: graceful shutdown timeout (in milliseconds)
            container.setShutdownTimeout(10_000L) // 10 seconds
        );

    // Optional: concurrency settings
    factory.setConcurrentConsumers(3);
    factory.setMaxConcurrentConsumers(10);

    return factory;
  }
}
