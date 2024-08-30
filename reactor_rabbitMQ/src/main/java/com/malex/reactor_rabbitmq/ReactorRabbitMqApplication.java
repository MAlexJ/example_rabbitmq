package com.malex.reactor_rabbitmq;

import com.rabbitmq.client.Connection;
import jakarta.annotation.PreDestroy;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import reactor.core.publisher.Mono;

@SpringBootApplication
@RequiredArgsConstructor
public class ReactorRabbitMqApplication {

  private final Mono<Connection> brokerConnection;

  // Make sure the connection before the program is finished
  @PreDestroy
  public void close() throws Exception {
    Objects.requireNonNull(brokerConnection.block(), "AMQ connection not exist").close();
  }

  public static void main(String[] args) {
    SpringApplication.run(ReactorRabbitMqApplication.class, args);
  }
}
