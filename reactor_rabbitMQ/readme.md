### Reactor RabbitMQ Reference Guide

Reactor RabbitMQ is a reactive API for RabbitMQ based on Reactor
and RabbitMQ Java Client.

Reactor RabbitMQ API enables messages to be published to RabbitMQ and consumed from RabbitMQ using functional APIs
with non-blocking back-pressure and very low overheads.

###### This section describes the reactive API for producing and consuming messages using RabbitMQ.

###### There are two main classes in Reactor RabbitMQ:

1. reactor.rabbitmq.Sender for publishing messages to RabbitMQ
2. reactor.rabbitmq.Receiver for consuming messages from RabbitMQ

link: https://projectreactor.io/docs/rabbitmq/snapshot/reference/#api-guide-receiver

### Reactive API for RabbitMQ

With the fundamentals of RabbitMQ and the Spring WebFlux covered,
the last piece of the puzzle is the Reactive API for RabbitMQ.

If you are building a reactive application, you want the non-blocking back-pressure feature
but the RabbitMQ does not provide such API.

Reactor RabbitMQ is a reactive API for RabbitMQ
and it is based on the Reactor project and the RabbitMQ Java Client.

```
Reactor RabbitMQ API enables messages to be published to RabbitMQ
and consumed from RabbitMQ using functional APIs with non-blocking back-pressure and very low overheads.
Reactor RabbitMQ is not intended to replace any of the existing Java libraries.
Instead, it is aimed at providing an alternative API for reactive event-driven applications.

  — Reactor RabbitMQ
```

link: https://medium.com/@jcrmorelli/how-to-implement-reactor-rabbitmq-in-your-reactive-spring-boot-application-part-2-733564b9f6d5

1. additional dependencies:

```
    // https://mvnrepository.com/artifact/io.projectreactor.rabbitmq/reactor-rabbitmq
    implementation 'io.projectreactor.rabbitmq:reactor-rabbitmq:1.5.6'
```

2. Configuration:

```
custom:
  rabbitmq:
    username: ${RABBITMQ_USERNAME:***}
    password: ${RABBITMQ_PASSWORD:***}
    port: ${RABBITMQ_PORT:5672}
    host: ${RABBITMQ_HOST:localhost}
    virtualhost: ${RABBITMQ_VIRTUAL_HOST:***}
    queue:
      request: async_remoute_call_queue
      reply: async_remoute_call_reply_queue
    exchange: async_remoute_call_exchange
    routing:
      key: async_remoute_call_routing_key
```

3. RabbitMQ Spring configuration

```
@Configuration
@RequiredArgsConstructor
public class RabbitMqConfiguration {

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

  @Bean
  public Mono<Connection> connectionMono(RabbitProperties rabbitProperties) {
    ConnectionFactory connectionFactory = new ConnectionFactory();
    connectionFactory.setHost(host);
    connectionFactory.setPort(port);
    connectionFactory.setUsername(username);
    connectionFactory.setPassword(password);
    connectionFactory.setVirtualHost(virtualHost);
    return Mono.fromCallable(() -> connectionFactory.newConnection(CONNECTION_NAME)).cache();
  }

  @Bean
  public Sender sender(Mono<Connection> connectionMono) {
    return RabbitFlux.createSender(new SenderOptions().connectionMono(connectionMono));
  }

  @Bean
  public Receiver receiver(Mono<Connection> connectionMono) {
    return RabbitFlux.createReceiver(new ReceiverOptions().connectionMono(connectionMono));
  }

  @Bean
  public Flux<Delivery> deliveryFlux(Receiver receiver) {
    return receiver.consumeAutoAck(queue);
  }
}
```