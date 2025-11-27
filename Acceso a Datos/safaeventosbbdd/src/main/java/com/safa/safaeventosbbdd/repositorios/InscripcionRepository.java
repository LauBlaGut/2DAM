package com.safa.safaeventosbbdd.repositorios;

import com.safa.safaeventosbbdd.modelos.Inscripcion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface InscripcionRepository extends JpaRepository <Inscripcion, Integer>{
    List<Inscripcion> findByUsuario_Id(Integer usuario);

    List<Inscripcion> findByEvento_Id(Integer evento);

    Inscripcion findByUsuario_IdAndEvento_Id(Integer usuario, Integer evento);

    boolean existsByUsuario_IdAndEvento_Id(Integer usuario, Integer evento);

}
