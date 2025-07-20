package com.malexj.configuration;

import java.util.HashMap;
import java.util.Map;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ConnectionFactoryConfiguration {

  @Value("${custom.rabbitmq.username}")
  private String username;

  @Value("${custom.rabbitmq.password}")
  private String password;

  @Value("${custom.rabbitmq.host}")
  private String host;

  @Value("${custom.rabbitmq.port}")
  private Integer port;

  @Value("${custom.rabbitmq.virtualhost}")
  private String virtualHost;

  @Bean
  public CachingConnectionFactory connectionFactory() {
    final var connectionFactory = buildCachingConnectionFactory();

    /*
     * Enable Publisher Confirms and Returns
     * This ensures your application knows whether the message reached the broker and whether it was routed correctly.
     */
    connectionFactory.setPublisherConfirmType(CachingConnectionFactory.ConfirmType.CORRELATED);
    connectionFactory.setPublisherReturns(true);

    /*
     * Set Connection & Channel Cache Size
     * Tune cache size based on expected concurrency and load.
     */
    connectionFactory.setChannelCacheSize(25);
    connectionFactory.setCacheMode(CachingConnectionFactory.CacheMode.CHANNEL);

    /*
     * Enable Connection and Channel Recovery (default for RabbitMQ Java client ≥5.0)
     * Spring AMQP doesn't require you to manually enable it in most cases, but for clarity:
     */
    connectionFactory.getRabbitConnectionFactory().setAutomaticRecoveryEnabled(true);

    /*
     * Set Client Properties (Optional but Helpful for Monitoring)
     */
    Map<String, Object> clientProperties = new HashMap<>();
    clientProperties.put("connection_name", "spring-app-producer");
    connectionFactory.getRabbitConnectionFactory().setClientProperties(clientProperties);

    return connectionFactory;
  }

  private CachingConnectionFactory buildCachingConnectionFactory() {
    final var connectionFactory = new CachingConnectionFactory();
    connectionFactory.setHost(host);
    connectionFactory.setPort(port);
    connectionFactory.setUsername(username);
    connectionFactory.setPassword(password);
    connectionFactory.setVirtualHost(virtualHost);
    return connectionFactory;
  }
}
