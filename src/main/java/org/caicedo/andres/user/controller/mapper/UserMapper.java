package org.caicedo.andres.user.controller.mapper;

import static org.mapstruct.MappingConstants.ComponentModel.SPRING;

import java.util.List;
import org.caicedo.andres.user.controller.model.User;
import org.caicedo.andres.user.repository.model.UserEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = SPRING)
public interface UserMapper {

  User toDomain(UserEntity userEntity);

  List<User> toDomain(List<UserEntity> userEntities);
}
