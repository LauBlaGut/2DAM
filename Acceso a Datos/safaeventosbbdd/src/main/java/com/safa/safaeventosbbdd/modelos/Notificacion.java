package com.safa.safaeventosbbdd.modelos;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@EqualsAndHashCode
@NoArgsConstructor
@ToString
@Entity
@Table(name = "notificacion", schema = "safaeventos")

public class Notificacion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_usuario")
    private Usuario usuario;

    @Column(name = "mensaje")
    private String mensaje;

    @Column(name = "fecha_envio")
    private Date fechaEnvio;

    @Column(name = "leido")
    private boolean leido;
}

