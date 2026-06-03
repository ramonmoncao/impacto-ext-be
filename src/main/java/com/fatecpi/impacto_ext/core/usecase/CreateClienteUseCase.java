package com.fatecpi.impacto_ext.core.usecase;

import com.fatecpi.impacto_ext.core.model.Cliente;
import com.fatecpi.impacto_ext.core.usecase.boundary.CreateClienteBoundary;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class CreateClienteUseCase {

    private final CreateClienteBoundary createClienteBoundary;

    public CreateClienteUseCase(CreateClienteBoundary createClienteBoundary) {
        this.createClienteBoundary = createClienteBoundary;
    }

    public String execute(Cliente cliente) {
        // Gera um ID caso o cliente seja novo
        if (cliente.getId() == null) {
            cliente.setId(UUID.randomUUID().toString());
        }
        return createClienteBoundary.execute(cliente);
    }
}