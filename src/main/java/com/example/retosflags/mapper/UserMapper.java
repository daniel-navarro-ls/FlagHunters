package com.example.retosflags.mapper;

import java.util.Collection;
import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.example.retosflags.dto.UserAuthDTO;
import com.example.retosflags.dto.UserDTO;
import com.example.retosflags.model.User;
@Mapper(componentModel="spring")
public interface UserMapper {
    UserDTO toDTO(User user);
    @Mapping(target ="comentarios",ignore=true)
    @Mapping(target = "password", ignore = true)
    User toDomain(UserDTO userDTO);
    List<UserDTO> toDTOs(Collection<User> users);
    @Mapping(target = "id", ignore = true)
    User toDomainAuth(UserAuthDTO userAuthDTO);
}
