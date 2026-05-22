package com.fatecpi.impacto_ext.controller;

import com.fatecpi.impacto_ext.controller.mapper.UserRequestMapper;
import com.fatecpi.impacto_ext.controller.request.UserRequest;
import com.fatecpi.impacto_ext.controller.response.UserResponse;
import com.fatecpi.impacto_ext.core.model.User;
import com.fatecpi.impacto_ext.core.usecase.boundary.CreateUserBoundary;
import com.fatecpi.impacto_ext.core.usecase.boundary.LoginUserBoundary;
import com.fatecpi.impacto_ext.models.LoginRequest;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController {
    private final CreateUserBoundary createUserBoundary;
    private final LoginUserBoundary loginUserBoundary;

    @PostMapping("/register")
    public ResponseEntity<UUID> register(@RequestBody UserRequest request) {
        User user = UserRequestMapper.INSTANCE.toUser(request);
        UUID id = createUserBoundary.execute(user);
        return ResponseEntity.status(HttpStatus.CREATED).body(id);
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody LoginRequest request) {
        String jwt = loginUserBoundary.execute(request.getEmail(), request.getSenha());
        return ResponseEntity.ok(jwt);
    }
}
