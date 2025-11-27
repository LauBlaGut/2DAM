package com.safa.safaeventosbbdd.repositorios;

import com.safa.safaeventosbbdd.modelos.ComentarioEvento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ComentarioEventoRepository extends JpaRepository<ComentarioEvento, Integer>{
    List<ComentarioEvento> findByEvento_Id(Integer evento);

    List<ComentarioEvento> findByUsuario_Id(Integer usuario);

    List<ComentarioEvento> findByEvento_IdAndUsuario_Id(Integer evento, Integer usuario);
}
