package com.safa.safaeventosbd.dto;
import java.time.LocalDate;
import com.safa.safaeventosbd.enumerados.Curso;

public record PerfilDTO(
        Long id,
        Long usuarioId,
        String nombre,
        String apellidos,
        Curso curso,
        LocalDate fechaRegistro
) {}