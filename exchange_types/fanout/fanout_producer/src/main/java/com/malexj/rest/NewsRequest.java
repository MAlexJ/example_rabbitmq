package com.malexj.rest;

import java.util.Objects;

public record NewsRequest(String title, String content) {

  public NewsRequest {
    Objects.requireNonNull(title, "title cannot be null");
    Objects.requireNonNull(content, "content cannot be null");
  }
}
