package com.safa.safaeventosbd.repositorios;

import com.safa.safaeventosbd.modelos.FotoEvento;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FotoEventoRepository extends JpaRepository<FotoEvento, Long> {
    List<FotoEvento> findByInscripcion_Id(Long inscripcionId);
}
