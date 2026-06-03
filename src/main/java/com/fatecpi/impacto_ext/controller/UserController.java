package com.fatecpi.impacto_ext.controller;

import com.fatecpi.impacto_ext.controller.mapper.UserRequestMapper;
import com.fatecpi.impacto_ext.controller.request.UserRequest;
import com.fatecpi.impacto_ext.core.model.User;
import com.fatecpi.impacto_ext.core.usecase.boundary.CreateUserBoundary;
import com.fatecpi.impacto_ext.core.usecase.boundary.LoginUserBoundary;
import com.fatecpi.impacto_ext.models.LoginRequest;
import com.fatecpi.impacto_ext.models.Usuario;
import com.fatecpi.impacto_ext.repositories.UsuarioRepository; // Import correto da sua pasta

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController {
    private final CreateUserBoundary createUserBoundary;
    private final LoginUserBoundary loginUserBoundary;
    private final UsuarioRepository usuarioRepository; // Repositório injetado aqui

    @PostMapping("/register")
    public ResponseEntity<UUID> register(@RequestBody UserRequest request) {
        User user = UserRequestMapper.INSTANCE.toUser(request);
        UUID id = createUserBoundary.execute(user);
        return ResponseEntity.status(HttpStatus.CREATED).body(id);
    }

    @PostMapping("/login")
    public ResponseEntity<Map<String, String>> login(@RequestBody LoginRequest request) {
        // 1. Gera o Token
        String jwt = loginUserBoundary.execute(request.getEmail(), request.getSenha());
        
        // 2. Busca o nome do banco
        Usuario usuario = usuarioRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

        // 3. Monta a resposta JSON
        Map<String, String> response = new HashMap<>();
        response.put("token", jwt);
        response.put("email", usuario.getEmail());
        response.put("nome", usuario.getNome()); // Manda o nome real para o celular

        return ResponseEntity.ok(response);
    }
}