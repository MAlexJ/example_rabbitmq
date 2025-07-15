package com.malexj.consumer;

import java.time.LocalDateTime;

public record Event(Integer id, String message, LocalDateTime timestamp) {}
