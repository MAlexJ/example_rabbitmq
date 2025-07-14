package com.malexj.configuration;

import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.Delivery;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.amqp.RabbitProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.rabbitmq.RabbitFlux;
import reactor.rabbitmq.Receiver;
import reactor.rabbitmq.ReceiverOptions;
import reactor.rabbitmq.Sender;
import reactor.rabbitmq.SenderOptions;

@Configuration
@RequiredArgsConstructor
public class ConnectionFactoryConfiguration {

  private static final String CONNECTION_NAME = "reactor-rabbit";

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

  @Value("${custom.rabbitmq.queue}")
  private String queue;

  /*
   * the mono for connection, it is cached to re-use the connection across sender and receiver instances
   * this should work properly in most cases
   */
  @Bean
  public Mono<Connection> connectionFactory(RabbitProperties rabbitProperties) {
    ConnectionFactory factory = new ConnectionFactory();
    factory.setHost(host);
    factory.setPort(port);
    factory.setUsername(username);
    factory.setPassword(password);
    factory.setVirtualHost(virtualHost);
    return Mono.fromCallable(() -> factory.newConnection(CONNECTION_NAME)).cache();
  }

  //  Use RabbitProperties ...
  //  @Bean
  //  public Mono<Connection> connectionFactory(RabbitProperties rabbitProperties) {
  //    ...
  //    return Mono.fromCallable(() -> factory.newConnection(CONNECTION_NAME)).cache();
  //  }

  @Bean
  public Sender sender(Mono<Connection> connectionFactory) {
    var options = new SenderOptions().connectionMono(connectionFactory);
    return RabbitFlux.createSender(options);
  }

  @Bean
  public Receiver receiver(Mono<Connection> connectionFactory) {
    var options = new ReceiverOptions().connectionMono(connectionFactory);
    return RabbitFlux.createReceiver(options);
  }

  @Bean
  public Flux<Delivery> deliveryFlux(Receiver receiver) {
    return receiver
        // 1.           .consumeManualAck(queue)
        // 2.           .consumeAutoAck(queue)
        // 2.           .consumeNoAck(queue)
        .consumeAutoAck(queue);
  }
}
