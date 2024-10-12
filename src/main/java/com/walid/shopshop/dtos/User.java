package com.walid.shopshop.dtos;

import lombok.Data;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Data
public class User {
    private String firstName;
    private String lastName;
    private String email;
    private final boolean emailVerified = true;
    private final boolean enabled = true;
    private final Map<String, String> credentials;
    private final List<String> groups;

    public User(String firstName, String lastName, String email, String password) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.credentials = new HashMap<>(){{
            put("type", "password");
            put("value", password);
            put("temporary", "false");
        }};
//        this.credentials.put("type", "password");
//        this.credentials.put("value", password);
//        this.credentials.put("temporary", "false");
        this.groups = new ArrayList<>(List.of("users"));
    }

}
