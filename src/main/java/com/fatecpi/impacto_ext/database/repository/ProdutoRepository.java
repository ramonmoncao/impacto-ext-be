package com.fatecpi.impacto_ext.database.repository;

import com.fatecpi.impacto_ext.database.entity.ProdutoEntity;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ProdutoRepository extends MongoRepository<ProdutoEntity, String> {
}
