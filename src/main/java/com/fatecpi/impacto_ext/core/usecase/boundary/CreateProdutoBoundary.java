package com.fatecpi.impacto_ext.core.usecase.boundary;

import com.fatecpi.impacto_ext.core.model.Produto;

import java.util.UUID;

public interface CreateProdutoBoundary {

    UUID execute(Produto produto);
}
