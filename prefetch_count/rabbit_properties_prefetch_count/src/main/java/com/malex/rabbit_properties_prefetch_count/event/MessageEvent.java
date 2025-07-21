package com.malex.rabbit_properties_prefetch_count.event;

import java.util.Objects;

public record MessageEvent(Integer id, String text) {
  public MessageEvent {
    Objects.requireNonNull(id, "the 'id' field cannot be null");
    Objects.requireNonNull(text, "the 'text' field cannot be null");
  }
}
