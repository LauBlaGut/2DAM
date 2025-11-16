package com.safa.safaeventosbbdd.repositorios;

import com.safa.safaeventosbbdd.modelos.MeInteresa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MeInteresaRepository extends JpaRepository<MeInteresa, Integer> {
    List<MeInteresa> findByIdUsuario_Id(Integer idUsuario);

    List<MeInteresa> findByIdEvento_Id(Integer idEvento);

    MeInteresa findByIdUsuario_IdAndIdEvento_Id(Integer idUsuario, Integer idEvento);
}
