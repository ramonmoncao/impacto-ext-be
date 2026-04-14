package com.fatecpi.impacto_ext.database.repository;

import com.fatecpi.impacto_ext.database.entity.UserEntity;
import org.springframework.data.mongodb.repository.MongoRepository;
import java.util.Optional;
import java.util.UUID;

public interface UserRepository extends MongoRepository<UserEntity, UUID> {
    Optional<UserEntity> findByEmail(String email);
    Optional<UserEntity> findByCpf(String cpf);
}
