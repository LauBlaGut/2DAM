package com.safa.safaeventosbd.modelos;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@EqualsAndHashCode
@NoArgsConstructor
@ToString
@Entity
@Table(name = "inscripcion")

public class Inscripcion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "id_usuario", nullable = false)
    private Usuario usuario;

    @ManyToOne(optional = false)
    @JoinColumn(name = "id_evento", nullable = false)
    private Evento evento;

    @Column(name = "pago_realizado")
    private boolean pagoRealizado = false;

    @Column(name = "metodo_pago", length = 100)
    private String metodoPago;

    @Column(name = "tiene_coste", nullable = false)
    private boolean tieneCoste = false;
}
