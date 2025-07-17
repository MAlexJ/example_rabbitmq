package com.malexj.rest;

import java.time.LocalDateTime;

public record NewsResponse (Integer id, String title, String content, LocalDateTime timestamp){}
