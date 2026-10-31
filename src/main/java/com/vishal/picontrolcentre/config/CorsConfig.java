package com.vishal.picontrolcentre.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CorsConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/app/**")
                .allowedOrigins("http://192.168.1.20:5173")
                .allowedMethods("GET")
                .allowedHeaders("*");
    }
}
