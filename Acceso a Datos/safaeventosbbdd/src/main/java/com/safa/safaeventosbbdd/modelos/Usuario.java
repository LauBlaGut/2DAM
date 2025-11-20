package com.safa.safaeventosbbdd.modelos;

import com.safa.safaeventosbbdd.modelos.enums.RolUsuario;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@EqualsAndHashCode
@NoArgsConstructor
@ToString
@Entity
@Table(name = "usuario", schema="safaeventos")

public class Usuario implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "email")
    private String email;

    @Column(name = "contrasenia")
    private String contrasenia;

    @Column(name = "rol")
    private RolUsuario rol;

    @Column(name = "verificacion")
    private Boolean verificacion;

    @OneToMany(mappedBy = "idUsuario", fetch = FetchType.LAZY)
    private Set<ComentarioEvento> comentariosEvento;

    @OneToMany(mappedBy = "idUsuario", fetch = FetchType.LAZY)
    private Set<Evento> eventos;

    @OneToMany(mappedBy = "idUsuario", fetch = FetchType.LAZY)
    private Set<FotoEvento> fotosEvento;

    @OneToMany(mappedBy = "idUsuario", fetch = FetchType.LAZY)
    private Set<Inscripcion> inscripciones;

    @OneToMany(mappedBy = "idUsuario", fetch = FetchType.LAZY)
    private Set<MeInteresa> meInteresas;

    @OneToMany(mappedBy = "idUsuario", fetch = FetchType.LAZY)
    private Set<Notificacion> notificaciones;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_perfil")
    private Perfil idPerfil;



    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(this.rol.name()));
    }

    @Override
    public String getPassword() {
        return this.contrasenia;
    }

    @Override
    public String getUsername() {
        return this.email; // login con email
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return this.verificacion; // o true si no usas verificación
    }
}
