package com.fatecpi.impacto_ext.database.impl;

import com.fatecpi.impacto_ext.core.model.Cliente;
import com.fatecpi.impacto_ext.core.usecase.boundary.CreateClienteBoundary;
import com.fatecpi.impacto_ext.database.entity.ClienteEntity;
import com.fatecpi.impacto_ext.database.mapper.ClienteEntityMapper;
import com.fatecpi.impacto_ext.database.repository.ClienteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@RequiredArgsConstructor
public class CreateClienteGateway implements CreateClienteBoundary {

    private final ClienteRepository repository;

    @Override
    public String execute(Cliente cliente) {
        ClienteEntityMapper mapper = ClienteEntityMapper.INSTANCE;
        ClienteEntity entity = mapper.toEntity(cliente);
        
        ClienteEntity saved = repository.save(entity);
        return saved.getId();
    }
}