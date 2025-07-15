package com.malexj.configuration.websocket;

import com.malexj.websocket.EventWebSocketHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

/*
 * WebSocket configuration class for registering WebSocket handlers.
 * Enables WebSocket support and configures endpoint for sensor data streaming.
 */
@Configuration
@EnableWebSocket
@RequiredArgsConstructor
public class WebSocketConfiguration implements WebSocketConfigurer {

  /*
   * The endpoint path for events WebSocket connections
   */
  private static final String EVENTS_WS_PATH = "/events";

  /*
   * The WebSocket handler managing event messages
   */
  private final EventWebSocketHandler webSocketHandler;

  /*
   * Registers the WebSocket handler for events at the configured endpoint
   */
  @Override
  public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
    registry
        .addHandler(webSocketHandler, EVENTS_WS_PATH)
        // CORS: Allow requests from any origin
        .setAllowedOrigins("*");
  }
}
