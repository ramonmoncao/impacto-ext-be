package com.fatecpi.impacto_ext.controller.mapper;

import com.fatecpi.impacto_ext.controller.request.UserRequest;
import com.fatecpi.impacto_ext.core.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface UserRequestMapper {
    UserRequestMapper INSTANCE = Mappers.getMapper(UserRequestMapper.class);
    User toUser(UserRequest request);
}

