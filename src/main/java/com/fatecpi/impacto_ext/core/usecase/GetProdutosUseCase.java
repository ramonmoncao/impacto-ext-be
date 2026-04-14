package com.fatecpi.impacto_ext.core.usecase;

import com.fatecpi.impacto_ext.core.model.Produto;
import com.fatecpi.impacto_ext.core.usecase.boundary.GetProdutosBoundary;
import com.fatecpi.impacto_ext.database.repository.ProdutoRepository;
import com.fatecpi.impacto_ext.database.entity.ProdutoEntity;
import com.fatecpi.impacto_ext.database.mapper.ProdutoMapperUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class GetProdutosUseCase implements GetProdutosBoundary {
    private final ProdutoRepository produtoRepository;

    @Override
    public List<Produto> execute() {
        return produtoRepository.findAll().stream()
                .map(ProdutoMapperUtil::toProdutoPolimorfico)
                .collect(Collectors.toList());
    }
}
