package com.fatecpi.impacto_ext.database.repository;

import com.fatecpi.impacto_ext.database.entity.ProdutoEntity;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.UUID;

public interface ProdutoRepository extends MongoRepository<ProdutoEntity, UUID> {
}
