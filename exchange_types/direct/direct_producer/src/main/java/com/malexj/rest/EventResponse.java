package com.malexj.rest;

import java.time.LocalDateTime;

public record EventResponse(Integer id, String message, LocalDateTime timestamp) {}
