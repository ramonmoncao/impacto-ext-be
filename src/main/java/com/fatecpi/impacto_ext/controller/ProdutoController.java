package com.fatecpi.impacto_ext.controller;

import com.fatecpi.impacto_ext.controller.mapper.ProdutoRequestMapper;
import com.fatecpi.impacto_ext.controller.request.ProdutoRequest;
import com.fatecpi.impacto_ext.controller.response.ExtintorResponse;
import com.fatecpi.impacto_ext.controller.response.ProdutoResponse;
import com.fatecpi.impacto_ext.core.model.Extintor;
import com.fatecpi.impacto_ext.core.model.Produto;
import com.fatecpi.impacto_ext.core.usecase.CreateProdutoUseCase;
import com.fatecpi.impacto_ext.core.usecase.boundary.GetProdutoByIdBoundary;
import com.fatecpi.impacto_ext.core.usecase.boundary.GetProdutosBoundary;
import com.fatecpi.impacto_ext.core.usecase.boundary.UpdateProdutoBoundary;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/products")
public class ProdutoController {

    private final CreateProdutoUseCase createProdutoUseCase;
    private final GetProdutosBoundary getProdutosUseCase;
    private final GetProdutoByIdBoundary getProdutoByIdUseCase;
    
    // NOVA INJEÇÃO AQUI
    private final UpdateProdutoBoundary updateProdutoUseCase;

    @PostMapping
    public ResponseEntity<String> create(@RequestBody ProdutoRequest request) {
        ProdutoRequestMapper mapper = ProdutoRequestMapper.INSTANCE;
        Produto produto = mapper.toProduto(request);
        String id = createProdutoUseCase.execute(produto);

        return ResponseEntity.status(HttpStatus.CREATED).body(id);
    }

    @GetMapping
    public ResponseEntity<List<Object>> getAll() {
        List<Object> produtos = getProdutosUseCase.execute().stream()
                .map(produto -> {
                    if (produto instanceof Extintor extintor) {
                        return ExtintorResponse.builder()
                                .id(extintor.getId())
                                .name(extintor.getName())
                                .price(extintor.getPrice())
                                .description(extintor.getDescription())
                                .estoque(extintor.getEstoque())
                                .type(extintor.getType())
                                .weight(extintor.getWeight() != null ? extintor.getWeight().getValue() : 0)
                                .status(extintor.getStatus())
                                .build();
                    } else {
                        return ProdutoResponse.builder()
                                .id(produto.getId())
                                .name(produto.getName())
                                .price(produto.getPrice())
                                .description(produto.getDescription())
                                .estoque(produto.getEstoque())
                                .build();
                    }
                })
                .toList();
        return ResponseEntity.ok(produtos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getById(@PathVariable String id) {
        Optional<Produto> produtoOpt = getProdutoByIdUseCase.execute(id);
        if (produtoOpt.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        Produto produto = produtoOpt.get();
        if (produto instanceof Extintor extintor) {
            ExtintorResponse response = ExtintorResponse.builder()
                    .id(extintor.getId())
                    .name(extintor.getName())
                    .price(extintor.getPrice())
                    .description(extintor.getDescription())
                    .estoque(extintor.getEstoque())
                    .type(extintor.getType())
                    .weight(extintor.getWeight() != null ? extintor.getWeight().getValue() : 0)
                    .status(extintor.getStatus())
                    .build();
            return ResponseEntity.ok(response);
        } else {
            ProdutoResponse response = ProdutoResponse.builder()
                    .id(produto.getId())
                    .name(produto.getName())
                    .price(produto.getPrice())
                    .description(produto.getDescription())
                    .estoque(produto.getEstoque())
                    .build();
            return ResponseEntity.ok(response);
        }
    }

    // NOVA ROTA DE ATUALIZAÇÃO (PUT)
    @PutMapping("/{id}")
    public ResponseEntity<String> update(@PathVariable String id, @RequestBody ProdutoRequest request) {
        ProdutoRequestMapper mapper = ProdutoRequestMapper.INSTANCE;
        Produto produto = mapper.toProduto(request);
        
        // Garante que o ID da URL será usado na atualização
        produto.setId(id);
        
        try {
            String updatedId = updateProdutoUseCase.execute(produto);
            return ResponseEntity.ok(updatedId);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
}