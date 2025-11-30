package com.woofly.courseservice.config;

import feign.Logger;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FeignConfig {
    @Bean
    Logger.Level feignLoggerLevel() {
        // FULL: Request və Response-un hər şeyini (Header, Body) göstərir
        return Logger.Level.FULL;
    }
}