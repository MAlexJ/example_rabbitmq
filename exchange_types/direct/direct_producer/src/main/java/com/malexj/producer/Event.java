package com.malexj.producer;

import java.time.LocalDateTime;

public record Event(Integer id, String message, LocalDateTime timestamp) {}
