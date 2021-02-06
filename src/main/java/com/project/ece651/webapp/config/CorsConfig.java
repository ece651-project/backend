package com.project.ece651.webapp.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CorsConfig {

    // CORS ref:
    // https://developer.mozilla.org/en-US/docs/Web/HTTP/CORS
    // https://spring.io/guides/gs/rest-service-cors/
    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                // TODO: add URLs
                // Path pattern ref: https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/web/util/pattern/PathPattern.html
                registry.addMapping("/**").allowedOrigins("http://localhost:8080");
            }
        };
    }
}
