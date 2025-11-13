package com.safa.safaeventosbd.dto;

public record ComentarioEventoDTO(
        Long id,
        Long inscripcionId,
        String comentario,
        String fechaComentario
) {}
