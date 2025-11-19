package com.example.retosflags.mapper;

import com.example.retosflags.dto.ComentarioDTO;
import com.example.retosflags.dto.RetoDTO;
import com.example.retosflags.dto.UserDTO;
import com.example.retosflags.model.Reto;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-11-19T10:13:24+0100",
    comments = "version: 1.6.3, compiler: Eclipse JDT (IDE) 3.44.0.v20251023-0518, environment: Java 21.0.8 (Eclipse Adoptium)"
)
@Component
public class RetoMapperImpl implements RetoMapper {

    @Autowired
    private UserMapper userMapper;
    @Autowired
    private ComentarioMapper comentarioMapper;

    @Override
    public RetoDTO toDTO(Reto reto) {
        if ( reto == null ) {
            return null;
        }

        UserDTO user = null;
        List<ComentarioDTO> comentarios = null;
        Long id = null;
        String titulo = null;
        String descripcion = null;
        String enlace = null;
        String flag = null;

        user = userMapper.toDTO( reto.getUser() );
        comentarios = comentarioMapper.toDTOs( reto.getComentarios() );
        id = reto.getId();
        titulo = reto.getTitulo();
        descripcion = reto.getDescripcion();
        enlace = reto.getEnlace();
        flag = reto.getFlag();

        RetoDTO retoDTO = new RetoDTO( id, titulo, descripcion, enlace, flag, user, comentarios );

        return retoDTO;
    }

    @Override
    public Reto toDomain(RetoDTO retoDTO) {
        if ( retoDTO == null ) {
            return null;
        }

        Reto reto = new Reto();

        reto.setId( retoDTO.id() );
        reto.setTitulo( retoDTO.titulo() );
        reto.setDescripcion( retoDTO.descripcion() );
        reto.setEnlace( retoDTO.enlace() );
        reto.setFlag( retoDTO.flag() );

        return reto;
    }
}
