package com.fatecpi.impacto_ext.models;

import lombok.Data;

@Data
public class LoginRequest {
    private String email;
    private String senha;
}