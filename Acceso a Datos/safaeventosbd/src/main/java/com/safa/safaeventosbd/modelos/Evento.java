package com.safa.safaeventosbd.modelos;

import com.safa.safaeventosbd.enumerados.CategoriaEvento;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@EqualsAndHashCode
@NoArgsConstructor
@ToString
@Entity
@Table(name = "evento")

public class Evento {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "titulo", nullable = false, length = 200)
    private String titulo;

    @Column(name = "descripcion", nullable = false, columnDefinition = "TEXT")
    private String descripcion;

    @Column(name = "fecha", nullable = false)
    private LocalDate fecha;

    @Column(name = "ubicacion", nullable = false, length = 200)
    private String ubicacion;

    @Column(name = "precio", columnDefinition = "NUMERIC(10,2)")
    private Double precio = 0.0;

    @Enumerated(EnumType.ORDINAL)
    @Column(name = "categoria", nullable = false)
    private CategoriaEvento categoria;

    @ManyToOne(optional = false)
    @JoinColumn(name = "id_organizador", nullable = false)
    private Usuario organizador;
}
