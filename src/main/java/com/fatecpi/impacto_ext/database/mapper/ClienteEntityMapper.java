package com.fatecpi.impacto_ext.database.mapper;

import com.fatecpi.impacto_ext.core.model.Cliente;
import com.fatecpi.impacto_ext.database.entity.ClienteEntity;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface ClienteEntityMapper {
    ClienteEntityMapper INSTANCE = Mappers.getMapper(ClienteEntityMapper.class);

    ClienteEntity toEntity(Cliente cliente);
    Cliente toCliente(ClienteEntity entity);
}