package com.malexj.configuration.rabbitmq.connection;

import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
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

  /*
   * ConnectionFactory implementation when the cache mode is CachingConnectionFactory.CacheMode.CHANNEL (default)
   * returns the same Connection from all createConnection() calls,
   * and ignores calls to com.rabbitmq.client.Connection.close() and caches Channel.
   */
  @Bean
  public ConnectionFactory connectionFactory() {
    final var connectionFactory = new CachingConnectionFactory();
    connectionFactory.setVirtualHost(virtualHost);
    connectionFactory.setHost(host);
    connectionFactory.setUsername(username);
    connectionFactory.setPort(port);
    connectionFactory.setPassword(password);
    return connectionFactory;
  }
}
