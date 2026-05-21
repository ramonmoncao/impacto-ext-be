package com.fatecpi.impacto_ext.core.usecase;

import com.fatecpi.impacto_ext.core.model.User;
import com.fatecpi.impacto_ext.core.usecase.boundary.LoginUserBoundary;
import com.fatecpi.impacto_ext.database.entity.UserEntity;
import com.fatecpi.impacto_ext.database.repository.UserRepository;
import com.fatecpi.impacto_ext.database.mapper.UserEntityMapper;
import com.fatecpi.impacto_ext.security.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LoginUserUseCase implements LoginUserBoundary {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    @Override
    public String execute(String email, String senha) {
        UserEntityMapper mapper = UserEntityMapper.INSTANCE;
        Authentication authentication = new UsernamePasswordAuthenticationToken(email, senha);
        try {
            authenticationManager.authenticate(authentication);
        } catch (AuthenticationException e) {
            throw new RuntimeException("Credenciais inválidas");
        }
        UserEntity userEntity = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));
        User user = mapper.toUser(userEntity);
        return jwtService.generateToken(user);
    }
}

