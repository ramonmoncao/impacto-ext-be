package com.fatecpi.impacto_ext.controller;

import com.fatecpi.impacto_ext.models.AuthResponse;
import com.fatecpi.impacto_ext.models.LoginRequest;
import com.fatecpi.impacto_ext.security.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "*")
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private UserDetailsService userDetailsService;

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody LoginRequest request) {
        // 1. Verifica se a senha e o email batem com o que o Ramon salvou no banco
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getEmail(), request.getSenha())
        );

        // 2. Se a senha estiver correta, busca os detalhes do usuário
        UserDetails userDetails = userDetailsService.loadUserByUsername(request.getEmail());

        // 3. Gera o Token JWT blindado
        String token = jwtService.generateToken((com.fatecpi.impacto_ext.core.model.User) userDetails);

        // 4. Devolve o token para o aplicativo guardar no celular
        return ResponseEntity.ok(new AuthResponse(token));
    }
}