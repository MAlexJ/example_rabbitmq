package com.malexj.model;

import java.util.Objects;

public record MessageRequest(Integer id, String message) {

  public MessageRequest {
    Objects.requireNonNull(id, "id cannot be null");
    Objects.requireNonNull(message, "message cannot be null");
  }
}
