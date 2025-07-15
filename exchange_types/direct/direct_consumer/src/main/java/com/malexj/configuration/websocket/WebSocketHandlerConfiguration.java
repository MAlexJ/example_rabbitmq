package com.malexj.configuration.websocket;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.malexj.consumer.Event;
import com.malexj.websocket.EventWebSocketHandler;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicReference;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.WebSocketSession;

/*
 * Configuration class responsible for defining beans related to WebSocket message handling,
 * including session management and the handler used to process sensor data messages.
 */
@Configuration
public class WebSocketHandlerConfiguration {

  /*
   * Exposes a thread-safe list to store active WebSocket sessions.
   * This allows other components to access and manage the currently connected clients.
   */
  @Bean
  public List<WebSocketSession> webSocketSessions() {
    return new CopyOnWriteArrayList<>();
  }

  /*
   * Creates a WebSocket handler bean for managing sensor data communication.
   * This handler is responsible for sending sensor messages to connected WebSocket clients.
   */
  @Bean
  public EventWebSocketHandler webSocketHandler(
      ObjectMapper objectMapper,
      AtomicReference<Event> eventReferenceStorage,
      List<WebSocketSession> webSocketSessions) {
    return new EventWebSocketHandler(objectMapper, webSocketSessions, eventReferenceStorage);
  }
}
