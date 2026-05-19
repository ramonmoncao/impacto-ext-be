package com.fatecpi.impacto_ext.core.usecase;

import com.fatecpi.impacto_ext.core.model.Extintor;
import com.fatecpi.impacto_ext.core.usecase.boundary.CreateExtintorBoundary;
import com.fatecpi.impacto_ext.database.entity.ProdutoEntity;
import com.fatecpi.impacto_ext.database.repository.ProdutoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CreateExtintorUseCase implements CreateExtintorBoundary {
    private final ProdutoRepository produtoRepository;

    @Override
    public UUID execute(Extintor extintor) {
        ProdutoEntity entity = ProdutoEntity.builder()
                .id(UUID.randomUUID())
                .name(extintor.getName())
                .price(extintor.getPrice())
                .description(extintor.getDescription())
                .estoque(extintor.getEstoque())
                .type(extintor.getType())
                .weight(extintor.getWeight())
                .status(extintor.getStatus())
                .build();
        produtoRepository.save(entity);
        return entity.getId();
    }
}
