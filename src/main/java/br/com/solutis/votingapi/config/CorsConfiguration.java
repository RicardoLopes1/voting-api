package br.com.solutis.votingapi.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CorsConfiguration implements WebMvcConfigurer {

  @Override
  public void addCorsMappings(CorsRegistry registry) {
    int oneHourInSeconds = 60 * 60;
    registry.addMapping("/**")
      .allowedOrigins("*")
      .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS", "HEAD", "PATCH", "TRACE", "CONNECT")
      .allowedHeaders("Content-Type", "Authorization", "X-Requested-With", "accept", "Origin", "Access-Control-Request-Method", "Access-Control-Request-Headers")
      .exposedHeaders("Access-Control-Allow-Origin", "Access-Control-Allow-Credentials")
      .maxAge(oneHourInSeconds);
  }
  
}
