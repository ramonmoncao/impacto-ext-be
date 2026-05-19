package com.fatecpi.impacto_ext.controller.mapper;

import com.fatecpi.impacto_ext.controller.request.ProdutoRequest;
import com.fatecpi.impacto_ext.core.model.Produto;
import com.fatecpi.impacto_ext.database.entity.ProdutoEntity;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface ProdutoRequestMapper {

    public static final ProdutoRequestMapper INSTANCE = Mappers.getMapper(ProdutoRequestMapper.class);

    Produto toProduto(ProdutoRequest request);

}
