package com.example.retosflags.dto;

import java.util.List;

public record RetoDTO(
    Long id,
    String titulo,
    String descripcion,
    String enlace,
    String flag,
    UserDTO user,
    List<ComentarioDTO> comentarios
){
    
}
