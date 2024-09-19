package com.walid.shopshop.config;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;

import java.util.Collection;
import java.util.Map;
import java.util.stream.Collectors;

public class KeycloakJwtAuthenticationConverter extends JwtAuthenticationConverter {


    public Collection<SimpleGrantedAuthority> extractAuthorities(Jwt jwt) {
        // Extract roles from the 'resource_access' field of the JWT
        Map<String, Object> resourceAccess = jwt.getClaim("resource_access");
        if (resourceAccess != null && resourceAccess.containsKey("shopshop")) {
            Map<String, Object> clientRoles = (Map<String, Object>) resourceAccess.get("shopshop");
            return ((Collection<String>) clientRoles.get("roles")).stream()
                    .map(SimpleGrantedAuthority::new)
                    .collect(Collectors.toList());
        }

        // Fallback to extracting roles from 'realm_access' if 'resource_access' is not present
        return ((Collection<String>) jwt.getClaim("realm_access")).stream()
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());
    }
}
