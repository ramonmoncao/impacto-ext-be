package com.fatecpi.impacto_ext.models;

import lombok.Data;

@Data
public class AuthResponse {
    private String token;
    private String nome; // <-- NOVO CAMPO
    
    // Construtor atualizado para receber o nome
    public AuthResponse(String token, String nome) {
        this.token = token;
        this.nome = nome;
    }
}