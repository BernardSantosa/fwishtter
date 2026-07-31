package com.fwishtter.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fwishtter.helper.ObjectHelper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class JacksonConfig {

    @Bean
    public ObjectMapper objectMapper() {
        // Uses your existing ObjectHelper which configures JavaTimeModule for dates/times
        return ObjectHelper.objectMapper();
    }
}