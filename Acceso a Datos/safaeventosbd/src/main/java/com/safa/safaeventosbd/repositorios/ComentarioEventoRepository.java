package com.safa.safaeventosbd.repositorios;

import com.safa.safaeventosbd.modelos.ComentarioEvento;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ComentarioEventoRepository extends JpaRepository<ComentarioEvento, Long> {
    List<ComentarioEvento> findByInscripcion_Id(Long inscripcionId);
}
