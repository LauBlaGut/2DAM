package com.safa.safaeventosbbdd.modelos;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@EqualsAndHashCode
@NoArgsConstructor
@ToString
@Entity
@Table(name = "comentario_evento", schema = "safaeventos")


public class ComentarioEvento {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="id_usuario")
    private Usuario usuario;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="id_evento")
    private Evento evento;


    @Column(name = "comentario")
    private String comentario;

    @Column(name = "fecha_comentario")
    private LocalDateTime fechaComentario;
}

