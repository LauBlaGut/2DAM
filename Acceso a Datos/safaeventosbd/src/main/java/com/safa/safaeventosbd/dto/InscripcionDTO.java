package com.safa.safaeventosbd.dto;

public record InscripcionDTO(
        Long id,
        Long usuarioId,
        Long eventoId,
        boolean pagoRealizado,
        String metodoPago,
        boolean tieneCoste
) {}
