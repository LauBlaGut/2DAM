package com.safa.safaeventosbbdd.config;

import org. springframework.context.annotation.Bean;
import org. springframework.context.annotation.Configuration;
import org. springframework.web.servlet.config.annotation.CorsRegistry;
import org. springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig {

    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/ ** ") // aplica a todos los endpoints
                        .allowedOrigins("http://localhost:8100") // URL de tu app Ionic
                        .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
                        .allowedHeaders("*")
                        .allowCredentials(true); // si necesitas cookies o JWT

            }
        };
    }
}
