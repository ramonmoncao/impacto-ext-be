package com.fatecpi.impacto_ext.database.mapper;

import com.fatecpi.impacto_ext.core.model.Extintor;
import com.fatecpi.impacto_ext.core.model.Produto;
import com.fatecpi.impacto_ext.database.entity.ProdutoEntity;

public class ProdutoMapperUtil {

    public static Produto toProdutoPolimorfico(ProdutoEntity entity) {
        if (entity == null) return null;

        // Se o campo de enum do tipo de extintor estiver preenchido, mapeia como Extintor
        if (entity.getType() != null) {
            Extintor extintor = new Extintor();
            extintor.setId(entity.getId());
            extintor.setName(entity.getName());
            extintor.setPrice(entity.getPrice());
            extintor.setDescription(entity.getDescription());
            extintor.setEstoque(entity.getEstoque());
            extintor.setType(entity.getType());
            extintor.setStatus(entity.getStatus());
            return extintor;
        } else {
            Produto produto = new Produto();
            produto.setId(entity.getId());
            produto.setName(entity.getName());
            produto.setPrice(entity.getPrice());
            produto.setDescription(entity.getDescription());
            produto.setEstoque(entity.getEstoque());
            return produto;
        }
    }
}