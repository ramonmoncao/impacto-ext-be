package com.fatecpi.impacto_ext.core.usecase.boundary;

import com.fatecpi.impacto_ext.core.model.Produto;
import java.util.List;

public interface GetProdutosBoundary {
    List<Produto> execute();
}

