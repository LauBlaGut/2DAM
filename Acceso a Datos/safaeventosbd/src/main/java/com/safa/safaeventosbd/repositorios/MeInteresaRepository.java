package com.safa.safaeventosbd.repositorios;

import com.safa.safaeventosbd.modelos.MeInteresa;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface MeInteresaRepository extends JpaRepository<MeInteresa, Long> {

    List<MeInteresa> findByUsuario_Id(Long usuarioId);

    Optional<MeInteresa> findByUsuario_IdAndEvento_Id(Long usuarioId, Long eventoId);

    boolean existsByUsuario_IdAndEvento_Id(Long usuarioId, Long eventoId);
}