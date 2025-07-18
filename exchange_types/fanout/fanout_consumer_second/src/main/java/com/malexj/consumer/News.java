package com.malexj.consumer;

import java.time.LocalDateTime;

public record News(Integer id, String title, String content, LocalDateTime timestamp) {}
