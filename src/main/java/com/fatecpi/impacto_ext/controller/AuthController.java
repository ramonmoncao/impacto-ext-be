package com.fatecpi.impacto_ext.controller;

import com.fatecpi.impacto_ext.models.AuthResponse;
import com.fatecpi.impacto_ext.models.LoginRequest;
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

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody LoginRequest request) {
        // Delega a responsabilidade para o UseCase que já funciona perfeitamente no seu projeto
        String token = loginUserUseCase.execute(request.getEmail(), request.getSenha());
        
        // Devolve o token formatado para o front-end
        return ResponseEntity.ok(new AuthResponse(token));
    }
}