package com.fatecpi.impacto_ext.controller.mapper;

import com.fatecpi.impacto_ext.controller.request.ExtintorRequest;
import com.fatecpi.impacto_ext.core.model.Extintor;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface ExtintorRequestMapper {
    ExtintorRequestMapper INSTANCE = Mappers.getMapper(ExtintorRequestMapper.class);
    Extintor toExtintor(ExtintorRequest request);
}

