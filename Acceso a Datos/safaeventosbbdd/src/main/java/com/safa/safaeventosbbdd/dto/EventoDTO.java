package com.safa.safaeventosbbdd.dto;

import com.safa.safaeventosbbdd.modelos.enums.CategoriaEventos;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EventoDTO {
    private Integer id;
    private String titulo;
    private String descripcion;
    private LocalDate fecha;
    private LocalTime hora;
    private String ubicacion;
    private Double precio;
    private CategoriaEventos categoriaEventos;
}
