package com.walid.shopshop.controllers;

import com.walid.shopshop.dtos.TokenResponse;
import com.walid.shopshop.dtos.User;
import com.walid.shopshop.services.KeycloakApiService;
import com.walid.shopshop.dtos.LoginRequest;
import com.walid.shopshop.dtos.RegisterRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;

import java.util.List;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/api")
public class AuthController {

    @Autowired
    private KeycloakApiService keycloakApiService;

    //token endpoint
    @GetMapping("/token")
    public ResponseEntity<TokenResponse> getToken() {
        try {
            TokenResponse token = keycloakApiService.getAdminToken();
            return ResponseEntity.ok(token);
        } catch (HttpClientErrorException e) {
            System.err.println("HTTP error: " + e.getStatusCode() + " " + e.getResponseBodyAsString());
            throw e;
        }
    }

    //refresh-token endpoint
    @PostMapping("/refresh-token")
    public ResponseEntity<TokenResponse> refreshToken(@RequestBody String refreshToken) {
        try {
            TokenResponse token = keycloakApiService.refreshToken(refreshToken);
            return ResponseEntity.ok(token);
        } catch (Exception e) {
            System.err.println("HTTP error: " + e.getMessage());
            return ResponseEntity.badRequest().body(new TokenResponse());
        }
    }

    // Registration endpoint
    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody RegisterRequest registerRequest) {
        try {
            User newUser = new User(registerRequest.getFirstName(), registerRequest.getLastName(), registerRequest.getEmail(), registerRequest.getPassword());
            keycloakApiService.registerUser(newUser);
            return ResponseEntity.status(HttpStatus.CREATED).body("User registered successfully");
        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
            return ResponseEntity.badRequest().body("Error registering user");
        }
    }

    // Login endpoint
    @PostMapping("/login")
    public ResponseEntity<TokenResponse> login(@RequestBody LoginRequest loginRequest) {
        try {
            TokenResponse token = keycloakApiService.login(loginRequest.getEmail(), loginRequest.getPassword());
            return ResponseEntity.ok(token);
        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
            return ResponseEntity.badRequest().body(new TokenResponse());
        }
    }

    //get users endpoint
    @GetMapping("/users")
    public ResponseEntity<List<Object>> getUsers() {
        List<Object> users = keycloakApiService.getUsers();
        if (users.isEmpty()) {
            return ResponseEntity.noContent().build(); // 204 No Content
        }
        return ResponseEntity.ok(users); // 200 OK
    }

    //    logout endpoint
    @PostMapping("/logout")
    public ResponseEntity<String> logout(@RequestBody String userId) {
        try {
            keycloakApiService.logout(userId);
            return ResponseEntity.ok("Logged out successfully");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error logging out");
        }
    }

    @PostMapping("/exchange-code")
    public ResponseEntity<TokenResponse> exchangeCode(@RequestBody String code) {
        try {
            TokenResponse token = keycloakApiService.exchangeCode(code);
            return ResponseEntity.ok(token);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new TokenResponse());
        }
    }
}

