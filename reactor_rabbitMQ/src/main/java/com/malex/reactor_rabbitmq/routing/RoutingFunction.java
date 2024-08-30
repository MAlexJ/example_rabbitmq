package com.malex.reactor_rabbitmq.routing;

import static org.springframework.web.reactive.function.server.RequestPredicates.POST;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

import com.malex.reactor_rabbitmq.handler.RoutingHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

@Component
public class RoutingFunction {

  @Bean
  public RouterFunction<ServerResponse> routes(RoutingHandler handler) {
    return route(POST("/events"), handler::create);
  }
}
