package com.interview.prep.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CorsConfig implements WebMvcConfigurer {

  @Value("${APP_ALLOWED_ORIGINS}")
  private String allowedOrigins;


  @Override
  public void addCorsMappings(CorsRegistry registry) {
    String[] origins = allowedOrigins.split("\\s*,\\s*");
    registry.addMapping("/**")
      // allow your frontend(s)
      .allowedOrigins(origins)
      .allowedMethods("GET","POST","PUT","PATCH","DELETE","OPTIONS")
      .allowedHeaders("*")
      .exposedHeaders("Location")
      .allowCredentials(false)   // set true only if you need cookies/Authorization to be sent
      .maxAge(3600);
  }
}
