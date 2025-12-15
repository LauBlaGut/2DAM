package com.safa.safaeventosbbdd.dto;

import com.safa.safaeventosbbdd.modelos.enums.CategoriaEventos;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.LocalDateTime;

public class EventoTopDTO {
    private Integer id;
    private String titulo;
    private String descripcion;
    private LocalDateTime fechaHora;
    private String ubicacion;
    private BigDecimal precio;
    private String foto;
    private Short categoria;
    private Integer asistentes;


    public EventoTopDTO(Integer id, String titulo, String descripcion, Timestamp fechaHora, String ubicacion, BigDecimal precio, String foto, Short categoria, Long asistentes) {
        this.id = id;
        this.titulo = titulo;
        this.descripcion = descripcion;
        this.fechaHora = (fechaHora != null) ? fechaHora.toLocalDateTime() : null;
        this.ubicacion = ubicacion;
        this.precio = precio;
        this.foto = foto;
        this.categoria = categoria;
        this.asistentes = asistentes != null ? asistentes.intValue() : null;
    }

    // Getters estándar para exponer los datos en la API
    public Integer getId() { return id; }
    public String getTitulo() { return titulo; }
    public String getDescripcion() { return descripcion; }
    public LocalDateTime getFechaHora() { return fechaHora; }
    public String getUbicacion() { return ubicacion; }
    public BigDecimal getPrecio() { return precio; }
    public String getFoto() { return foto; }
    public Short getCategoria() { return categoria; }
    public Integer getAsistentes() { return asistentes; }
}
