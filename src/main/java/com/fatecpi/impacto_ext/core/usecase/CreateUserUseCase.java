package com.fatecpi.impacto_ext.core.usecase;

import com.fatecpi.impacto_ext.core.model.User;
import com.fatecpi.impacto_ext.core.usecase.boundary.CreateUserBoundary;
import com.fatecpi.impacto_ext.database.entity.UserEntity;
import com.fatecpi.impacto_ext.database.mapper.UserEntityMapper;
import com.fatecpi.impacto_ext.database.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CreateUserUseCase implements CreateUserBoundary {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public UUID execute(User user) {
        UserEntityMapper mapper = UserEntityMapper.INSTANCE;
        // Verifica se já existe usuário com o mesmo CPF
        if (userRepository.findByCpf(user.getCpf()).isPresent()) {
            throw new RuntimeException("Já existe um usuário com este CPF");
        }
        user.setSenha(passwordEncoder.encode(user.getSenha()));
        UserEntity entity = mapper.toEntity(user);
        entity.setId(UUID.randomUUID());
        userRepository.save(entity);
        return entity.getId();
    }
}
