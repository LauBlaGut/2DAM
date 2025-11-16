package com.safa.safaeventosbbdd.repositorios;

import com.safa.safaeventosbbdd.modelos.ComentarioEvento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ComentarioEventoRepository extends JpaRepository<ComentarioEvento, Integer>{
    List<ComentarioEvento> findByIdEvento_Id(Integer idEvento);

    List<ComentarioEvento> findByIdUsuario_Id(Integer idUsuario);

    List<ComentarioEvento> findByIdEvento_IdAndIdUsuario_Id(Integer idEvento, Integer idUsuario);
}
