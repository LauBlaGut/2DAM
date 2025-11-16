package com.safa.safaeventosbbdd.modelos;

import com.safa.safaeventosbbdd.modelos.enums.MetodoPago;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@EqualsAndHashCode
@NoArgsConstructor
@ToString
@Entity
@Table(name = "inscripcion", schema = "safaeventos")

public class Inscripcion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_usuario")
    private Usuario idUsuario;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_evento")
    private Evento idEvento;

    @Column(name = "pago_realizado")
    private boolean pagoRealizado;

    @Column(name = "metodo_pago")
    private MetodoPago metodoPago;

    @Column(name = "tiene_coste")
    private boolean tieneCoste;
}