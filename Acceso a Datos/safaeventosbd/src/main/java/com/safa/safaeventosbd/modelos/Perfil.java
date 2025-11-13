package com.safa.safaeventosbd.modelos;

import com.safa.safaeventosbd.enumerados.Curso;
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
@Table(name = "perfil")

public class Perfil {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @OneToOne(optional = false)
    @JoinColumn(name = "id_usuario", nullable = false)
    private Usuario usuario;

    @Column(name = "nombre", nullable = false, length = 100)
    private String nombre;

    @Column(name = "apellidos", nullable = false, length = 150)
    private String apellidos;

    @Enumerated(EnumType.ORDINAL)
    @Column(name = "curso")
    private Curso curso;

    @Column(name = "fecha_registro", nullable = false)
    private LocalDate fechaRegistro;


}
