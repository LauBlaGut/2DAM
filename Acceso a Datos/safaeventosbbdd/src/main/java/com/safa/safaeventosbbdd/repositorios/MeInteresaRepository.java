package com.safa.safaeventosbbdd.repositorios;

import com.safa.safaeventosbbdd.modelos.MeInteresa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MeInteresaRepository extends JpaRepository<MeInteresa, Integer> {
    List<MeInteresa> findByUsuario_Id(Integer usuario);

    List<MeInteresa> findByEvento_Id(Integer evento);

    MeInteresa findByUsuario_IdAndEvento_Id(Integer usuario, Integer evento);

    boolean existsByUsuarioIdAndEventoId(Integer usuario, Integer evento);

}
