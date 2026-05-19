package com.fatecpi.impacto_ext.database.impl;

import com.fatecpi.impacto_ext.core.model.Produto;
import com.fatecpi.impacto_ext.core.usecase.boundary.CreateProdutoBoundary;
import com.fatecpi.impacto_ext.database.entity.ProdutoEntity;
import com.fatecpi.impacto_ext.database.mapper.ProdutoEntityMapper;
import com.fatecpi.impacto_ext.database.repository.ProdutoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@RequiredArgsConstructor
public class CreateProdutoGateway implements CreateProdutoBoundary {

    private final ProdutoRepository repository;

    @Override
    public UUID execute(Produto produto) {
        ProdutoEntityMapper mapper = ProdutoEntityMapper.INSTANCE;
        ProdutoEntity entity = mapper.toEntity(produto);
        entity.setId(UUID.randomUUID());
        ProdutoEntity saved = repository.insert(entity);
        return saved.getId();
    }
}
