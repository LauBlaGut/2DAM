package com.safa.safaeventosbbdd.dto;

import com.safa.safaeventosbbdd.modelos.enums.CategoriaEventos;

import java.time.LocalDateTime;

public interface EventoTopDTO {
    Integer getId();
    String getTitulo();
    String getDescripcion();
    LocalDateTime getFechaHora();
    String getUbicacion();
    Double getPrecio();
    String getFoto();
    CategoriaEventos getCategoria();
    Integer getAsistentes();
}
