package com.example.retosflags.mapper;

import com.example.retosflags.dto.ComentarioDTO;
import com.example.retosflags.dto.UserDTO;
import com.example.retosflags.model.Comentario;
import com.example.retosflags.model.Reto;
import com.example.retosflags.model.User;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-11-19T10:13:24+0100",
    comments = "version: 1.6.3, compiler: Eclipse JDT (IDE) 3.44.0.v20251023-0518, environment: Java 21.0.8 (Eclipse Adoptium)"
)
@Component
public class ComentarioMapperImpl implements ComentarioMapper {

    @Override
    public ComentarioDTO toDTO(Comentario comentario) {
        if ( comentario == null ) {
            return null;
        }

        Long retoId = null;
        Long id = null;
        String comment = null;
        UserDTO user = null;

        retoId = comentarioRetoId( comentario );
        id = comentario.getId();
        comment = comentario.getComment();
        user = userToUserDTO( comentario.getUser() );

        ComentarioDTO comentarioDTO = new ComentarioDTO( id, comment, user, retoId );

        return comentarioDTO;
    }

    @Override
    public Comentario toDomain(ComentarioDTO comentarioDTO) {
        if ( comentarioDTO == null ) {
            return null;
        }

        Comentario comentario = new Comentario();

        comentario.setComment( comentarioDTO.comment() );

        return comentario;
    }

    @Override
    public List<ComentarioDTO> toDTOs(Collection<Comentario> comentarios) {
        if ( comentarios == null ) {
            return null;
        }

        List<ComentarioDTO> list = new ArrayList<ComentarioDTO>( comentarios.size() );
        for ( Comentario comentario : comentarios ) {
            list.add( toDTO( comentario ) );
        }

        return list;
    }

    private Long comentarioRetoId(Comentario comentario) {
        Reto reto = comentario.getReto();
        if ( reto == null ) {
            return null;
        }
        return reto.getId();
    }

    protected UserDTO userToUserDTO(User user) {
        if ( user == null ) {
            return null;
        }

        Long id = null;
        String username = null;

        id = user.getId();
        username = user.getUsername();

        UserDTO userDTO = new UserDTO( id, username );

        return userDTO;
    }
}
