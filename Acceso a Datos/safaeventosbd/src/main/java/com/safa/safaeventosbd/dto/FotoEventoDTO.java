package com.safa.safaeventosbd.dto;

public record FotoEventoDTO(
        Long id,
        Long inscripcionId,
        String rutaFoto,
        String fechaSubida
) {}
