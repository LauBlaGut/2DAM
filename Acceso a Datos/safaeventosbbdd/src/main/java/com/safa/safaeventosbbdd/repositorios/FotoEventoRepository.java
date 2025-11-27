package com.safa.safaeventosbbdd.repositorios;

import com.safa.safaeventosbbdd.modelos.FotoEvento;
import org.hibernate.type.descriptor.converter.spi.JpaAttributeConverter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FotoEventoRepository extends JpaRepository<FotoEvento, Integer> {
    List<FotoEvento> findByEvento_Id(Integer evento);

    List<FotoEvento> findByUsuario_Id(Integer usuario);

    List<FotoEvento> findByEvento_IdAndUsuario_Id(Integer evento, Integer usuario);
}
