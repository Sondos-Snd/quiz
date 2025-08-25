package com.interview.prep.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter;
import org.springframework.security.web.SecurityFilterChain;

import java.util.*;

@Configuration
@EnableMethodSecurity
public class SecurityConfig {

  @Bean
  SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
    http
      .csrf(csrf -> csrf.disable())
      .authorizeHttpRequests(auth -> auth
        .requestMatchers(
          "/api/answers/info",
          "/api/answers/",
          "/api/answers/test",
          "/api/answers/check",        // if you want to keep this open while testing, remove later
          "/error",
          "/actuator/health"
        ).permitAll()
        .requestMatchers("/api/secure/**").authenticated()
        .anyRequest().authenticated()
      )
      .oauth2ResourceServer(oauth2 -> oauth2
        .jwt(jwt -> jwt.jwtAuthenticationConverter(keycloakAuthoritiesConverter()))
      );
    return http.build();
  }

  /**
   * Map realm_access.roles and resource_access.<client>.roles -> ROLE_*
   * plus keep any scope authorities.
   */
  @Bean
  JwtAuthenticationConverter keycloakAuthoritiesConverter() {
    JwtGrantedAuthoritiesConverter scopes = new JwtGrantedAuthoritiesConverter();
    scopes.setAuthorityPrefix("SCOPE_");

    return new JwtAuthenticationConverter() {{
      setJwtGrantedAuthoritiesConverter((Jwt jwt) -> {
        Set<GrantedAuthority> auths = new HashSet<>(Objects.requireNonNullElse(scopes.convert(jwt), List.of()));

        Map<String, Object> realmAccess = jwt.getClaim("realm_access");
        if (realmAccess instanceof Map<?,?> ra) {
          Object roles = ra.get("roles");
          if (roles instanceof Collection<?> rs) {
            for (Object r : rs) auths.add(new SimpleGrantedAuthority("ROLE_" + r));
          }
        }
        Map<String, Object> resourceAccess = jwt.getClaim("resource_access");
        if (resourceAccess instanceof Map<?,?> rmap) {
          rmap.forEach((client, data) -> {
            if (data instanceof Map<?,?> d) {
              Object roles = d.get("roles");
              if (roles instanceof Collection<?> rs) {
                for (Object r : rs) auths.add(new SimpleGrantedAuthority("ROLE_" + r));
              }
            }
          });
        }
        return auths;
      });
    }};
  }
}
