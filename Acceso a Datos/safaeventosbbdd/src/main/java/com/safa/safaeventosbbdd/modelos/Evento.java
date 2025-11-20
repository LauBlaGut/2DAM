package com.safa.safaeventosbbdd.modelos;

import com.safa.safaeventosbbdd.modelos.enums.CategoriaEventos;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.Set;

@Entity
@Table(name = "evento", schema = "safaeventos")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode


public class Evento {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private Integer id;

    @Column (name="titulo")
    private String titulo;

    @Column(name = "descripcion")
    private String descripcion;

    @Column(name = "fecha_hora")
    private LocalDateTime fechaHora;

    @Column(name="foto")
    private String foto;

    @Column(name = "ubicacion")
    private String ubicacion;

    @Column(name = "precio")
    private Double precio;

    @Column(name = "categoria")
    private CategoriaEventos categoria;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_organizador")
    private Usuario idUsuario;

    @OneToMany(mappedBy = "idEvento", fetch = FetchType.LAZY)
    private Set<ComentarioEvento> comentariosEvento;

    @OneToMany(mappedBy = "idEvento", fetch = FetchType.LAZY)
    private Set<FotoEvento> fotosEvento;

    @OneToMany(mappedBy = "idEvento", fetch = FetchType.LAZY)
    private Set<Inscripcion> inscripciones;

    @OneToMany(mappedBy = "idEvento", fetch = FetchType.LAZY)
    private Set<MeInteresa> meInteresas;
}
