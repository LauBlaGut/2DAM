package com.safa.safaeventosbd.dto;

public record NotificacionDTO(
        Long id,
        Long usuarioId,
        String mensaje,
        String fechaEnvio,
        boolean leido
) {}
