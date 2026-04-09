package com.fatecpi.impacto_ext.database.mapper;

import com.fatecpi.impacto_ext.core.model.User;
import com.fatecpi.impacto_ext.database.entity.UserEntity;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface UserEntityMapper {
    UserEntityMapper INSTANCE = Mappers.getMapper(UserEntityMapper.class);
    User toUser(UserEntity entity);
    UserEntity toEntity(User user);
}

