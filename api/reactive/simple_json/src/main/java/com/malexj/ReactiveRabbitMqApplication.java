package com.malexj;

import com.rabbitmq.client.Connection;
import jakarta.annotation.PreDestroy;
import java.io.IOException;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import reactor.core.publisher.Mono;

@SpringBootApplication
@RequiredArgsConstructor
public class ReactiveRabbitMqApplication {

  private final Mono<Connection> brokerConnection;

  /*
   *  Make sure the connection before the program is finished
   */
  @PreDestroy
  public void close() throws IOException {
    Objects.requireNonNull(brokerConnection.block(), "AMQ connection not exist").close();
  }

  public static void main(String[] args) {
    SpringApplication.run(ReactiveRabbitMqApplication.class, args);
  }
}
