package com.example.retosflags.dto;

public record ComentarioDTO(
    Long id,
    String comment,
    UserDTO user,
    Long retoId
){

    
}
