package com.safa.safaeventosbd.dto;

public record MeInteresaDTO(
        Long id,
        Long usuarioId,
        Long eventoId,
        String fechaGuardado
) {}
