package com.walid.shopshop.config;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.web.SecurityFilterChain;

import lombok.extern.slf4j.Slf4j;

@Configuration
@EnableWebSecurity
@Slf4j
public class SecurityConfig {

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .csrf(AbstractHttpConfigurer::disable)
                .cors(Customizer.withDefaults())
                .authorizeHttpRequests(auth -> auth
                .requestMatchers("/api/v1/customers/**").hasRole("ADMIN")
                .requestMatchers("/api/v1/sales/**").hasRole("ADMIN")
                .requestMatchers("/api/v1/orders/{trackingNumber}").permitAll()
                .requestMatchers("/api/v1/orders/**").hasAnyRole("CUSTOMER", "ADMIN")
                .anyRequest().permitAll())
                .oauth2ResourceServer(oauth -> oauth.jwt(jwt -> jwt.jwtAuthenticationConverter(jwtAuthenticationConverterForKeycloak())))
                //                can be omitted since Spring Security will automatically detect when using JWT or OAuth2 and handle statelessness by default.
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .build();
    }

    @Bean
    public JwtAuthenticationConverter jwtAuthenticationConverterForKeycloak() {
        Converter<Jwt, Collection<GrantedAuthority>> jwtGrantedAuthoritiesConverter = jwt -> {
            // Extract realm roles
            Map<String, Object> realmAccess = jwt.getClaim("realm_access");
            Collection<String> realmRoles = new ArrayList<>();
            if (realmAccess != null && realmAccess.containsKey("roles")) {
                Object rolesObj = realmAccess.get("roles");
                if (rolesObj instanceof Collection<?> roles) {
                    realmRoles = roles.stream()
                        .filter(String.class::isInstance)
                        .map(String.class::cast)
                        .collect(Collectors.toList());
                }
            }
            // Extract client roles
            Map<String, Object> resourceAccess = jwt.getClaim("resource_access");
            Collection<String> clientRoles = new ArrayList<>();
            if (resourceAccess != null && resourceAccess.containsKey("shopshop")) {
                Map<String, Object> clientAccess = (Map<String, Object>) resourceAccess.get("shopshop");
                if (clientAccess.containsKey("roles")) {
                    clientRoles = (Collection<String>) clientAccess.get("roles");
                }
            }
            // Combine both realm and client roles
            Collection<String> allRoles = Stream.concat(realmRoles.stream(), clientRoles.stream())
                    .collect(Collectors.toSet());
            // Convert roles to GrantedAuthority
            return allRoles.stream()
                    .map(role -> new SimpleGrantedAuthority("ROLE_" + role))
                    .collect(Collectors.toList());
        };
        var jwtAuthenticationConverter = new JwtAuthenticationConverter();
        jwtAuthenticationConverter.setJwtGrantedAuthoritiesConverter(jwtGrantedAuthoritiesConverter);
        return jwtAuthenticationConverter;
    }

}
