package com.malexj.rest;

import java.util.Objects;

public record EventRequest(String message, String type) {

  public EventRequest {
    Objects.requireNonNull(message, "message not be null");
    Objects.requireNonNull(type, "type not be null");
  }
}
