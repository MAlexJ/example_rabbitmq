package com.malexj.rest;

import static org.springframework.web.reactive.function.server.RequestPredicates.POST;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

@Component
public class EventsRouting {

  @Bean
  public RouterFunction<ServerResponse> routes(EventsHandler handler) {
    return route(POST("/events"), handler::create);
  }
}
