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
@Table(name = "foto_evento", schema = "safaeventos")


public class FotoEvento {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="id_usuario")
    @JsonIgnore
    private Usuario usuario;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="id_evento")
    @JsonIgnore
    private Evento evento;

    @Column(name = "ruta_foto")
    private String rutaFoto;

    @Column(name = "fecha_subida")
    private LocalDateTime fechaSubida;
}
