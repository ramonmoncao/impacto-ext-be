package com.fatecpi.impacto_ext.core.usecase.boundary;

import com.fatecpi.impacto_ext.core.model.Cliente;
import java.util.UUID;

public interface CreateClienteBoundary {
    String execute(Cliente cliente);
}