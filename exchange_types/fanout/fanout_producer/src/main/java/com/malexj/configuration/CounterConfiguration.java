package com.malexj.configuration;

import java.util.concurrent.atomic.AtomicInteger;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CounterConfiguration {

    @Bean
    public AtomicInteger counter(){
        return new AtomicInteger(0);
    }
}
