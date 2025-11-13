package com.safa.safaeventosbd.modelos;

import com.safa.safaeventosbd.enumerados.RolUsuario;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@EqualsAndHashCode
@NoArgsConstructor
@ToString
@Entity
@Table(name = "usuario")

public class Usuario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "email", nullable = false, unique = true, length = 100)
    private String email;

    @Column(name = "contrasenia", nullable = false, length = 100)
    private String contrasenia;

    @Enumerated(EnumType.ORDINAL)
    @Column(name = "rol", nullable = false)
    private RolUsuario rol;

    @Column(name = "verificacion", nullable = false)
    private boolean verificacion = false;
    
}
