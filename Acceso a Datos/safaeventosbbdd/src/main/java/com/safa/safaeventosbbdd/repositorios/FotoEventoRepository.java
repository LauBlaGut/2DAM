package com.safa.safaeventosbbdd.repositorios;

import com.safa.safaeventosbbdd.modelos.FotoEvento;
import org.hibernate.type.descriptor.converter.spi.JpaAttributeConverter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FotoEventoRepository extends JpaRepository<FotoEvento, Integer> {
    List<FotoEvento> findByIdEvento_Id(Integer idEvento);

    List<FotoEvento> findByIdUsuario_Id(Integer idUsuario);

    List<FotoEvento> findByIdEvento_IdAndIdUsuario_Id(Integer idEvento, Integer idUsuario);
}
