package com.fatecpi.impacto_ext.controller;

import com.fatecpi.impacto_ext.controller.mapper.ProdutoRequestMapper;
import com.fatecpi.impacto_ext.controller.request.ProdutoRequest;
import com.fatecpi.impacto_ext.core.model.Produto;
import com.fatecpi.impacto_ext.core.usecase.CreateProdutoUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/products")
public class ProdutoController {

    private final CreateProdutoUseCase createProdutoUseCase;

    @PostMapping
    public ResponseEntity<UUID> create(@RequestBody ProdutoRequest request) {

        ProdutoRequestMapper mapper = ProdutoRequestMapper.INSTANCE;
        Produto produto = mapper.toProduto(request);
        UUID id = createProdutoUseCase.execute(produto);

        return ResponseEntity.status(HttpStatus.CREATED).body(id);
    }
}
