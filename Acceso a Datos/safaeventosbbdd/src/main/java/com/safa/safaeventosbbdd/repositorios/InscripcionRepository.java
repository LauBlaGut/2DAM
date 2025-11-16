package com.safa.safaeventosbbdd.repositorios;

import com.safa.safaeventosbbdd.modelos.Inscripcion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface InscripcionRepository extends JpaRepository <Inscripcion, Integer>{
    List<Inscripcion> findByIdUsuario_Id(Integer idUsuario);

    List<Inscripcion> findByIdEvento_Id(Integer idEvento);

    Inscripcion findByIdUsuario_IdAndIdEvento_Id(Integer idUsuario, Integer idEvento);
}
