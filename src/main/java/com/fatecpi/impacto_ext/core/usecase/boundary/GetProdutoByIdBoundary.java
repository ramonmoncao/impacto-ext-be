package com.fatecpi.impacto_ext.core.usecase.boundary;

import com.fatecpi.impacto_ext.core.model.Produto;
import java.util.Optional;
import java.util.UUID;

public interface GetProdutoByIdBoundary {
    Optional<Produto> execute(UUID id);
}

