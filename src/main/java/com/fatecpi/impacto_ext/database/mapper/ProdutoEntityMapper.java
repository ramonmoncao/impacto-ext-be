package com.fatecpi.impacto_ext.database.mapper;

import com.fatecpi.impacto_ext.core.model.Produto;
import com.fatecpi.impacto_ext.database.entity.ProdutoEntity;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface ProdutoEntityMapper {

    ProdutoEntityMapper INSTANCE = Mappers.getMapper(ProdutoEntityMapper.class);

    Produto toProduto(ProdutoEntity produtoEntity);
    ProdutoEntity toEntity(Produto produto);
}
