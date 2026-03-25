package com.fatecpi.impacto_ext.database.mapper;

import com.fatecpi.impacto_ext.core.model.Produto;
import com.fatecpi.impacto_ext.database.entity.ProdutoEntity;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface ProdutoEntityMapper {

    public static final ProdutoEntityMapper INSTANCE = Mappers.getMapper(ProdutoEntityMapper.class);

    Produto toProduto(ProdutoEntityMapper produtoEntity);
    ProdutoEntity toEntity(Produto produto);
}
