package com.safa.safaeventosbd.repositorios;

import com.safa.safaeventosbd.modelos.Perfil;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PerfilRepository extends JpaRepository<Perfil, Long> {
    Perfil findByUsuarioId(Long usuarioId);
}
