package com.safa.safaeventosbd.modelos;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@EqualsAndHashCode
@NoArgsConstructor
@ToString
@Entity
@Table(name = "comentario_evento")


public class ComentarioEvento {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @ManyToOne(optional = false)
    @JoinColumns({
            @JoinColumn(name = "id_usuario", referencedColumnName = "id_usuario", nullable = false),
            @JoinColumn(name = "id_evento", referencedColumnName = "id_evento", nullable = false)
    })
    private Inscripcion inscripcion;

    @Column(name = "comentario", nullable = false, columnDefinition = "TEXT")
    private String comentario;

    @Column(name = "fecha_comentario")
    private LocalDateTime fechaComentario = LocalDateTime.now();
}
