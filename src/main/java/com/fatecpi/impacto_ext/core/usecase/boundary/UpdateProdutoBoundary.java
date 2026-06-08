package com.fatecpi.impacto_ext.core.usecase.boundary;

import com.fatecpi.impacto_ext.core.model.Produto;

public interface UpdateProdutoBoundary {
    String execute(Produto produto);
}