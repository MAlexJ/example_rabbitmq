package com.malex.consumer;

import java.time.LocalDateTime;

public record Document(String uuid, String name, DocumentType type, LocalDateTime timestamp) {}
