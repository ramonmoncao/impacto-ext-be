package com.fatecpi.impacto_ext.core.usecase;

import com.fatecpi.impacto_ext.core.model.User;
import com.fatecpi.impacto_ext.core.usecase.boundary.LoginUserBoundary;
import com.fatecpi.impacto_ext.models.Usuario;
import com.fatecpi.impacto_ext.repositories.UsuarioRepository;
import com.fatecpi.impacto_ext.security.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LoginUserUseCase implements LoginUserBoundary {
    
    private final UsuarioRepository usuarioRepository;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    @Override
    public String execute(String email, String senha) {
        System.out.println("🔍 Tentando autenticar: " + email);
        System.out.println("🔍 Senha fornecida: " + senha);
        // Autentica o usuário com email e senha
        // Isso vai usar UserDetailsServiceImpl.loadUserByUsername()
        String hash = "$2a$10$vR.Bq4L5G1mN8kP2X9sY3uZ6Q4w5e7r1t4mL2c8dN5kP9vR3x.Hy";
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        boolean matches = encoder.matches(senha, hash);
        System.out.println("✅ BCrypt.matches('" + senha + "', hash) = " + matches);
        String newHash = encoder.encode("123456");
        System.out.println("✅ NOVO HASH PARA '123456': " + newHash);

        Authentication authentication = new UsernamePasswordAuthenticationToken(email, senha);
        try {
            authenticationManager.authenticate(authentication);
            System.out.println("✅ Autenticação bem-sucedida!");
        } catch (AuthenticationException e) {
            System.out.println("❌ Erro de autenticação: " + e.getMessage());
            throw new RuntimeException("Credenciais inválidas");
        }
        
        // Busca o usuário após autenticação bem-sucedida
        Usuario usuario = usuarioRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));
        
        // Converte para o modelo User do core com todos os parâmetros necessários
        // User(name, cpf, email, senha)
        User user = new User(usuario.getNome(), "", usuario.getEmail(), usuario.getSenha());
        
        // Gera e retorna o token JWT
        return jwtService.generateToken(user);
    }
}