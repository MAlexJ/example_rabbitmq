package com.malexj.websocket;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.malexj.consumer.Event;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

@Slf4j
@RequiredArgsConstructor
public class EventWebSocketHandler extends TextWebSocketHandler {

  private final ObjectMapper objectMapper;
  private final List<WebSocketSession> sessions;
  private final AtomicReference<Event> eventReferenceStorage;

  @Override
  public void afterConnectionEstablished(WebSocketSession session) {
    log.info("WebSocket connection established - session: {}", session);
    sessions.add(session);
    Optional.ofNullable(eventReferenceStorage.get())
        .ifPresent(
            event -> {
              var textMessage = convertToTextMessage(event);
              publishEvent(session, textMessage);
            });
  }

  @Override
  public void afterConnectionClosed(WebSocketSession session, CloseStatus status) {
    log.info("WebSocket connection closed - status: {}", status);
    sessions.remove(session);
  }

  public void publishEvent(Event message) {
    if (sessions.isEmpty()) {
      return;
    }
    var textMessage = convertToTextMessage(message);
    for (WebSocketSession session : sessions) {
      publishEvent(session, textMessage);
    }
  }

  private void publishEvent(WebSocketSession session, TextMessage textMessage) {
    try {
      log.info("Sending websocket message: {}", textMessage.getPayload());
      session.sendMessage(textMessage);
    } catch (Exception e) {
      throw new WebSocketHandlerException(
          "Error processing websocket message: '%s'".formatted(textMessage.getPayload()), e);
    }
  }

  private TextMessage convertToTextMessage(Event event) {
    try {
      var json = objectMapper.writeValueAsString(event);
      return new TextMessage(json);
    } catch (JsonProcessingException e) {
      throw new WebSocketHandlerException(
          "Error serializing message to json: '%s'".formatted(event), e);
    }
  }
}
