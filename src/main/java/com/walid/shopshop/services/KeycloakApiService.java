package com.walid.shopshop.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.walid.shopshop.dtos.TokenResponse;
import com.walid.shopshop.dtos.User;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
public class KeycloakApiService {

    @Value("${keycloak.auth-server-url}")
    private String keycloakAuthUrl;

    @Value("${keycloak.realm}")
    private String keycloakRealm;

    @Value("${keycloak.client-id}")
    private String clientId;

    @Value("${keycloak.client-secret}")
    private String clientSecret;

    @Value("${keycloak.admin-username}")
    private String adminUsername;

    @Value("${keycloak.admin-password}")
    private String adminPassword;

    private static final ObjectMapper objectMapper = new ObjectMapper();
    private final RestTemplate restTemplate = new RestTemplate();

    public void registerUser(User user) {
        String url = keycloakAuthUrl + "/admin/realms/" + keycloakRealm + "/users";
        HttpHeaders headers = new HttpHeaders();
        TokenResponse token = getAdminToken();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Authorization", "Bearer " + token.getAccess_token());
        JSONObject body = new JSONObject();
        body.put("firstName", user.getFirstName());
        body.put("lastName", user.getLastName());
        body.put("email", user.getEmail());
        body.put("emailVerified", user.isEmailVerified());
        body.put("enabled", user.isEnabled());
        body.put("credentials", new JSONArray().put(new JSONObject(user.getCredentials())));
        body.put("groups", new JSONArray(List.of("customers")));
        HttpEntity<String> entity = new HttpEntity<>(body.toString(), headers);
        try {
            restTemplate.exchange(url, HttpMethod.POST, entity, String.class);
        } catch (HttpClientErrorException e) {
            throw e;
        }
    }

    public TokenResponse login(String email, String password) {
        String url = keycloakAuthUrl + "/realms/" + keycloakRealm + "/protocol/openid-connect/token";
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        LinkedMultiValueMap<String, String> body = new LinkedMultiValueMap<>();
        body.add("client_id", clientId);
        body.add("client_secret", clientSecret);
        body.add("grant_type", "password");
        body.add("username", email);
        body.add("password", password);
        HttpEntity<LinkedMultiValueMap<String, String>> entity = new HttpEntity<>(body, headers);
        try {
            ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.POST, entity, String.class);
            return objectMapper.readValue(response.getBody(), TokenResponse.class);
        } catch (HttpClientErrorException e) {
            throw e;
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    public TokenResponse getAdminToken() {
        String url = keycloakAuthUrl + "/realms/" + keycloakRealm + "/protocol/openid-connect/token";
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        LinkedMultiValueMap<String, String> body = new LinkedMultiValueMap<>();
        body.add("client_id", clientId);         // Your client_id from Keycloak
        body.add("client_secret", clientSecret); // Your client_secret from Keycloak
        body.add("grant_type", "client_credentials");
        HttpEntity<LinkedMultiValueMap<String, String>> entity = new HttpEntity<>(body, headers);
        try {
            ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.POST, entity, String.class);
            return objectMapper.readValue(response.getBody(), TokenResponse.class);
        } catch (HttpClientErrorException e) {
            throw e;
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    public TokenResponse refreshToken(String refreshToken) {
        String url = keycloakAuthUrl + "/realms/" + keycloakRealm + "/protocol/openid-connect/token";
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        LinkedMultiValueMap<String, String> body = new LinkedMultiValueMap<>();
        body.add("client_id", clientId);
        body.add("client_secret", clientSecret);
        body.add("grant_type", "refresh_token");
        body.add("refresh_token", refreshToken);
        HttpEntity<LinkedMultiValueMap<String, String>> entity = new HttpEntity<>(body, headers);
        try {
            System.out.println("refresh "+ refreshToken);
            ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.POST, entity, String.class);
            return objectMapper.readValue(response.getBody(), TokenResponse.class);
        } catch (HttpClientErrorException e) {
            throw e;
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    public List<Object> getUsers() {
        String url = keycloakAuthUrl + "/admin/realms/" + keycloakRealm + "/users";
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);  // Changed to JSON
        TokenResponse token = getAdminToken();
        headers.set("Authorization", "Bearer " + token.getAccess_token());
        HttpEntity<String> entity = new HttpEntity<>(headers);
        try {
            ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, entity, String.class);
            JSONArray jsonResponse = new JSONArray(response.getBody());
            return jsonResponse.toList();
        } catch (HttpClientErrorException e) {
            throw e;
        }
    }

    public void logout(String userId) {
        String url = keycloakAuthUrl + "/admin/realms/" + keycloakRealm + "/users/" + userId + "/logout";
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        TokenResponse token = getAdminToken();
        headers.set("Authorization", "Bearer " + token.getAccess_token());
        HttpEntity<LinkedMultiValueMap<String, String>> entity = new HttpEntity<>(headers);
        try {
            restTemplate.exchange(url, HttpMethod.POST, entity, String.class);
        } catch (HttpClientErrorException e) {
            throw e;
        }
    }

    public TokenResponse exchangeCode(String code){
        String url = keycloakAuthUrl + "/realms/" + keycloakRealm + "/protocol/openid-connect/token";
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        LinkedMultiValueMap<String, String> body = new LinkedMultiValueMap<>();
        body.add("client_id", "shopshop-front");
//        body.add("client_secret", clientSecret);
        body.add("grant_type", "authorization_code");
        body.add("code", code);
        body.add("redirect_uri", "http://localhost:4200/home");
        HttpEntity<LinkedMultiValueMap<String, String>> entity = new HttpEntity<>(body, headers);
        try {
            ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.POST, entity, String.class);
            return objectMapper.readValue(response.getBody(), TokenResponse.class);
        } catch (HttpClientErrorException e) {
            throw e;
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

}
