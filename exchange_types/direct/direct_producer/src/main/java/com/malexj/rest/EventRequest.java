package com.malexj.rest;

import java.util.Objects;

public record EventRequest(String message) {

  public EventRequest {
    Objects.requireNonNull(message, "message is null");
  }
}
