package com.malexj.producer;

import java.util.Arrays;

public enum EventType {
  ORDER,
  USER;

  public static EventType findByName(String type) {
    return Arrays.stream(values())
        .filter(t -> t.name().equalsIgnoreCase(type))
        .findFirst()
        .orElseThrow(() -> new IllegalArgumentException("Invalid event type: %s".formatted(type)));
  }
}
