package com.fatecpi.impacto_ext.core.usecase;

import com.fatecpi.impacto_ext.core.model.Produto;
import com.fatecpi.impacto_ext.core.usecase.boundary.CreateProdutoBoundary;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@RequiredArgsConstructor
public class CreateProdutoUseCase {

    private final CreateProdutoBoundary createProdutoBoundary;

    public UUID execute(Produto produto) {
        if (produto.getPrice() <= 0) {
            throw new IllegalArgumentException("Preço deve ser maior que zero");
        }
        if (produto.getName() == null || produto.getName().isEmpty()) {
            throw new IllegalArgumentException("Nome é obrigatório");
        }

        return createProdutoBoundary.execute(produto);
    }

}
