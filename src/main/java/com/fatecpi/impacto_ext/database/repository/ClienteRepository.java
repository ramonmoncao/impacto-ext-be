package com.fatecpi.impacto_ext.database.repository;

import com.fatecpi.impacto_ext.database.entity.ClienteEntity;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClienteRepository extends MongoRepository<ClienteEntity, String> {
  
}