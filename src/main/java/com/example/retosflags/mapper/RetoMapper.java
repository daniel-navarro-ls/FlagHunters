package com.example.retosflags.mapper;

import java.util.List;
import java.util.stream.Collectors;
import java.util.Collection;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.example.retosflags.dto.RetoDTO;
import com.example.retosflags.model.Reto;
@Mapper(componentModel="spring", uses = {UserMapper.class, ComentarioMapper.class})
public interface RetoMapper {

    @Mapping(target= "user",source = "user")
    @Mapping(target = "comentarios", source = "comentarios")
    RetoDTO toDTO(Reto reto);
    @Mapping(target="comentarios",ignore=true)
    @Mapping(target="user",ignore = true)
    Reto toDomain(RetoDTO retoDTO);
    default List<RetoDTO> toDTOs(Collection<Reto> retos) {
        if (retos == null) {
            return null;
        }
        return retos.stream()
                   .map(this::toDTO)
                   .collect(Collectors.toList());
    }
}
