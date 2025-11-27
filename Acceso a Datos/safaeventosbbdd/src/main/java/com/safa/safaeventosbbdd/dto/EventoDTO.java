package com.safa.safaeventosbbdd.dto;

import com.safa.safaeventosbbdd.modelos.Evento;
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
    private CategoriaEventos categoria;
    private String foto;
    private Integer idOrganizador;


    public EventoDTO(Evento evento) {
        this.id = evento.getId();
        this.titulo = evento.getTitulo();
        this.descripcion = evento.getDescripcion();
        this.fecha = evento.getFechaHora().toLocalDate();
        this.hora = evento.getFechaHora().toLocalTime();
        this.ubicacion = evento.getUbicacion();
        this.precio = evento.getPrecio();
        this.categoria = evento.getCategoria();
        this.foto = evento.getFoto();
        if (evento.getUsuario() != null) {
            this.idOrganizador = evento.getUsuario().getId();
        }
    }
}


