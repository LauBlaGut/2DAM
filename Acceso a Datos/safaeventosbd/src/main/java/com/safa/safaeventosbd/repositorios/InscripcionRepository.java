package com.safa.safaeventosbd.repositorios;

import com.safa.safaeventosbd.modelos.Inscripcion;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface InscripcionRepository extends JpaRepository<Inscripcion, Long> {

    Optional<Inscripcion> findByUsuario_IdAndEvento_Id(Long idUsuario, Long idEvento);

    List<Inscripcion> findByUsuario_Id(Long idUsuario);

    List<Inscripcion> findByEvento_Id(Long idEvento);

    boolean existsByUsuario_IdAndEvento_Id(Long idUsuario, Long idEvento);
}
