package com.fatecpi.impacto_ext.database.repository;

import com.fatecpi.impacto_ext.database.entity.ClienteEntity;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClienteRepository extends MongoRepository<ClienteEntity, String> {
  List<ClienteEntity> findByNomeContainingIgnoreCaseOrCnpjContaining(String nome, String cnpj);
}