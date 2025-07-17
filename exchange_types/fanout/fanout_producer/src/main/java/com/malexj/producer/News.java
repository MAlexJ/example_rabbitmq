package com.malexj.producer;

import java.time.LocalDateTime;

public record News(Integer id, String title, String content, LocalDateTime timestamp) {}
