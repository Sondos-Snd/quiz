package com.interview.prep.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CorsConfig implements WebMvcConfigurer {
  @Override
  public void addCorsMappings(CorsRegistry registry) {
    registry.addMapping("/**")
      // allow your frontend(s)
      .allowedOrigins("https://quiz-gamma-silk.vercel.app")
      // If you also want local dev:
      // .allowedOrigins("https://quiz-gamma-silk.vercel.app", "http://localhost:4200")
      .allowedMethods("GET","POST","PUT","PATCH","DELETE","OPTIONS")
      .allowedHeaders("*")
      .exposedHeaders("Location")
      .allowCredentials(false)   // set true only if you need cookies/Authorization to be sent
      .maxAge(3600);
  }
}
