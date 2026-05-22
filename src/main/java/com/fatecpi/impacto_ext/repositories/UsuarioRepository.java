package com.fatecpi.impacto_ext.repositories;

import com.fatecpi.impacto_ext.models.Usuario;

import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UsuarioRepository extends MongoRepository<Usuario, String> {
    
    Optional<Usuario> findByEmail(String email);
    
}