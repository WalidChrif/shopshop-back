package com.walid.shopshop.dtos;

import lombok.Data;

@Data
public class LoginRequest {

    private String email;
    private String password;
}
