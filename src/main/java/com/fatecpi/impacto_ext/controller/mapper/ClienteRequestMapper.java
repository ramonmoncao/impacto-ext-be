package com.fatecpi.impacto_ext.controller.mapper;

import com.fatecpi.impacto_ext.controller.request.ClienteRequest;
import com.fatecpi.impacto_ext.core.model.Cliente;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface ClienteRequestMapper {
    ClienteRequestMapper INSTANCE = Mappers.getMapper(ClienteRequestMapper.class);

    Cliente toCliente(ClienteRequest request);
}