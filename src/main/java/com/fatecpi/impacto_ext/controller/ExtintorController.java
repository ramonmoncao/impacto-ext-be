package com.fatecpi.impacto_ext.controller;

import com.fatecpi.impacto_ext.controller.mapper.ExtintorRequestMapper;
import com.fatecpi.impacto_ext.controller.request.ExtintorRequest;
import com.fatecpi.impacto_ext.controller.response.ExtintorResponse;
import com.fatecpi.impacto_ext.core.model.Extintor;
import com.fatecpi.impacto_ext.core.usecase.boundary.CreateExtintorBoundary;
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
@RequestMapping("/extintores")
public class ExtintorController {
    private final CreateExtintorBoundary createExtintorBoundary;

    @PostMapping
    public ResponseEntity<String> create(@RequestBody ExtintorRequest request) {
        ExtintorRequestMapper mapper = ExtintorRequestMapper.INSTANCE;
        Extintor extintor = mapper.toExtintor(request);
        String id = createExtintorBoundary.execute(extintor);
        return ResponseEntity.status(HttpStatus.CREATED).body(id);
    }
}

