package com.fatecpi.impacto_ext.core.usecase;

import com.fatecpi.impacto_ext.core.model.Produto;
import com.fatecpi.impacto_ext.core.usecase.boundary.UpdateProdutoBoundary;
import com.fatecpi.impacto_ext.database.entity.ProdutoEntity;
import com.fatecpi.impacto_ext.database.repository.ProdutoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UpdateProdutoUseCase implements UpdateProdutoBoundary {

    private final ProdutoRepository repository;

    @Override
    public String execute(Produto produto) {
        // Busca o produto existente no banco de dados pelo ID
        Optional<ProdutoEntity> entityOpt = repository.findById(produto.getId());

        if (entityOpt.isPresent()) {
            ProdutoEntity entity = entityOpt.get();
            
            // Atualiza apenas os campos permitidos
            entity.setName(produto.getName());
            entity.setDescription(produto.getDescription());
            entity.setPrice(produto.getPrice());
            entity.setEstoque(produto.getEstoque());
            
            // Salva as alterações
            repository.save(entity);
            
            return entity.getId();
        } else {
            throw new RuntimeException("Produto com ID " + produto.getId() + " não encontrado.");
        }
    }
}