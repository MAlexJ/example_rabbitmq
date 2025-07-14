package com.malexj.rest;

import java.time.LocalDateTime;
import java.util.Objects;

public record EventResponse(String info, LocalDateTime dateTime) {

  public EventResponse {
    Objects.requireNonNull(info, "The info is required");
    Objects.requireNonNull(dateTime, "The date time is required");
  }
}
