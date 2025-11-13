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
@Table(name = "foto_evento")


public class FotoEvento {
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

    @Column(name = "ruta_foto", nullable = false, length = 255)
    private String rutaFoto;

    @Column(name = "fecha_subida")
    private LocalDateTime fechaSubida = LocalDateTime.now();
}
