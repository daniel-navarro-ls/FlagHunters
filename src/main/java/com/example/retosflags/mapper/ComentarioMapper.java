package com.example.retosflags.mapper;

import java.util.Collection;
import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.example.retosflags.dto.ComentarioDTO;
import com.example.retosflags.model.Comentario;

@Mapper(componentModel = "spring")
public interface ComentarioMapper {
    @Mapping(target="retoId",source="reto.id")
    ComentarioDTO toDTO(Comentario comentario);
    @Mapping(target = "reto", ignore = true)
    @Mapping(target = "user", ignore = true)
    Comentario toDomain(ComentarioDTO comentarioDTO);
    List<ComentarioDTO> toDTOs(Collection<Comentario> comentarios);
}
