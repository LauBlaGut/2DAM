package com.safa.safaeventosbd.dto;

import com.safa.safaeventosbd.enumerados.CategoriaEvento;

public record EventoDTO(
        Long id,
        String titulo,
        String descripcion,
        String fecha,
        String ubicacion,
        Double precio,
        CategoriaEvento categoria,
        Long organizadorId
) {}