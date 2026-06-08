package com.fatecpi.impacto_ext.core.usecase;

import com.fatecpi.impacto_ext.core.model.Produto;
import com.fatecpi.impacto_ext.core.usecase.boundary.GetProdutoByIdBoundary;
import com.fatecpi.impacto_ext.database.repository.ProdutoRepository;
import com.fatecpi.impacto_ext.database.mapper.ProdutoMapperUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class GetProdutoByIdUseCase implements GetProdutoByIdBoundary {
    private final ProdutoRepository produtoRepository;

    @Override
    public Optional<Produto> execute(String id) {
        return produtoRepository.findById(id)
                .map(ProdutoMapperUtil::toProdutoPolimorfico);
    }
}
