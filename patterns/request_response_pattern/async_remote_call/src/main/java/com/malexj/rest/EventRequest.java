package com.malexj.rest;

import java.util.Objects;

public record EventRequest(Integer id, String message) {

  public EventRequest {
    Objects.requireNonNull(id, "id cannot be null");
    Objects.requireNonNull(message, "message cannot be null");
  }
}
