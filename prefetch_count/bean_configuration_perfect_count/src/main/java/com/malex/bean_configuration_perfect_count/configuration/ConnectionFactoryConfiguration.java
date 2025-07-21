package com.malex.bean_configuration_perfect_count.configuration;

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
    final var connectionFactory = new CachingConnectionFactory();
    connectionFactory.setHost(host);
    connectionFactory.setPort(port);
    connectionFactory.setUsername(username);
    connectionFactory.setPassword(password);
    connectionFactory.setVirtualHost(virtualHost);

    /*
     * Set Client Properties (Optional but Helpful for Monitoring)
     */
    Map<String, Object> clientProperties = new HashMap<>();
    clientProperties.put(
        "configuration_perfect_count", "spring-app-bean_configuration_perfect_count");
    connectionFactory.getRabbitConnectionFactory().setClientProperties(clientProperties);
    return connectionFactory;
  }
}
