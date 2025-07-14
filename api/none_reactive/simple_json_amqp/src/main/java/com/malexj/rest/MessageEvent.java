package com.malexj.rest;

import java.util.Objects;

public record MessageEvent(Integer id, String text) {

  public MessageEvent {
    Objects.requireNonNull(id, "Id must not be null");
    Objects.requireNonNull(text, "Text must not be null");
  }
}
