package com.malexj.websocket;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.malexj.consumer.Event;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.WebSocketSession;

@Slf4j
public class EventWebSocketHandler extends AbstractEventWebSocketHandler {

  public EventWebSocketHandler(
      ObjectMapper objectMapper,
      List<WebSocketSession> sessions,
      AtomicReference<Event> eventReferenceStorage) {
    super(objectMapper, sessions, eventReferenceStorage);
  }

  @Override
  public void afterConnectionEstablished(WebSocketSession session) {
    log.info("WebSocket connected: sessionId={}", session.getId());
    sessions.add(session);
    sendLastEventIfExists(session);
  }

  @Override
  public void afterConnectionClosed(WebSocketSession session, CloseStatus status) {
    log.info(
        "WebSocket disconnected: sessionId={}, reason={}", session.getId(), status.getReason());
    sessions.remove(session);
  }

  @Override
  public void broadcast(Event event) {
    if (sessions.isEmpty()) {
      log.debug("No active WebSocket sessions. Skipping broadcast");
      return;
    }
    var textMessage = serializeEvent(event);
    for (WebSocketSession session : sessions) {
      sendMessageToSession(session, textMessage);
    }
  }
}
