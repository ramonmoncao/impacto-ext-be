package com.fatecpi.impacto_ext.controller;

import com.fatecpi.impacto_ext.models.AuthResponse;
import com.fatecpi.impacto_ext.models.LoginRequest;
import com.fatecpi.impacto_ext.models.Usuario; // Importar a entidade
import com.fatecpi.impacto_ext.repositories.UsuarioRepository; // Importar o repositório
import com.fatecpi.impacto_ext.core.usecase.LoginUserUseCase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "*")
public class AuthController {

    @Autowired
    private LoginUserUseCase loginUserUseCase;

    @Autowired
    private UsuarioRepository usuarioRepository; // <-- Injetando o repositório do Mongo

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody LoginRequest request) {
        // 1. Gera o token
        String token = loginUserUseCase.execute(request.getEmail(), request.getSenha());
        
        // 2. Busca o usuário no banco para pegar o nome real
        Usuario usuario = usuarioRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));
        
        // 3. Devolve o token e o nome para o front-end
        return ResponseEntity.ok(new AuthResponse(token, usuario.getNome()));
    }
}