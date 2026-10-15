package com.yuldashov.weathermvc.config;

import jdk.jfr.ContentType;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class WebClientConfig {

    @Bean
    public WebClient weatherWebClient() {
        return WebClient.builder()
                .baseUrl("https://api.openweathermap.org/data/2.5")
                .defaultHeader("Content-Type", "application/json")
                .defaultHeader("Accept", "application/json")
                .build();
    }

    @Bean
    public WebClient geoWebClient() {
        return WebClient.builder()
                .baseUrl("https://api.openweathermap.org/geo/1.0")
                .defaultHeader("Accept", "application/json")
                .build();
    }
}
