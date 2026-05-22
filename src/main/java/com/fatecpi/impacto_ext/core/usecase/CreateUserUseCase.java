package com.fatecpi.impacto_ext.core.usecase;

import com.fatecpi.impacto_ext.core.model.User;
import com.fatecpi.impacto_ext.core.usecase.boundary.CreateUserBoundary;
import com.fatecpi.impacto_ext.models.Usuario;
import com.fatecpi.impacto_ext.repositories.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CreateUserUseCase implements CreateUserBoundary {
    
    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public UUID execute(User user) {
        // Cria uma nova instância de Usuario
        Usuario usuario = new Usuario();
        String id = UUID.randomUUID().toString();
        usuario.setId(id);
        usuario.setNome(user.getName());
        usuario.setEmail(user.getEmail());
        
        // Criptografa a senha antes de salvar
        usuario.setSenha(passwordEncoder.encode(user.getSenha()));
        
        // Salva no repositório
        usuarioRepository.save(usuario);
        
        // Retorna UUID como esperado pela interface
        return UUID.fromString(id);
    }
}