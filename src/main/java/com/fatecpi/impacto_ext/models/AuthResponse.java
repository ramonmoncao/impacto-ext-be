package com.fatecpi.impacto_ext.models;

import lombok.Data;

@Data
public class AuthResponse {
    private String token;
    
    // Construtor para facilitar na hora de devolver a resposta
    public AuthResponse(String token) {
        this.token = token;
    }
}