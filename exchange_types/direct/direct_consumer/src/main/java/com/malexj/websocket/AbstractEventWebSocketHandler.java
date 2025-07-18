package com.malexj.websocket;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.malexj.consumer.Event;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

@Slf4j
@RequiredArgsConstructor
public abstract class AbstractEventWebSocketHandler extends TextWebSocketHandler {

  protected final ObjectMapper objectMapper;
  protected final List<WebSocketSession> sessions;
  protected final AtomicReference<Event> eventReferenceStorage;

  public abstract void broadcast(Event event);

  protected void sendLastEventIfExists(WebSocketSession session) {
    Optional.ofNullable(eventReferenceStorage.get())
        .ifPresent(
            event -> {
              var textMessage = serializeEvent(event);
              sendMessageToSession(session, textMessage);
            });
  }

  protected void sendMessageToSession(WebSocketSession session, TextMessage textMessage) {
    try {
      log.info(
          "Sending WebSocket message to sessionId={}: {}",
          session.getId(),
          textMessage.getPayload());
      session.sendMessage(textMessage);
    } catch (Exception e) {
      throw new WebSocketHandlerException(
          "Failed to send message to WebSocket sessionId=%s: '%s'"
              .formatted(session.getId(), textMessage.getPayload()),
          e);
    }
  }

  protected TextMessage serializeEvent(Event event) {
    try {
      var json = objectMapper.writeValueAsString(event);
      return new TextMessage(json);
    } catch (JsonProcessingException e) {
      throw new WebSocketHandlerException(
          "Error serializing message to json: '%s'".formatted(event), e);
    }
  }
}
