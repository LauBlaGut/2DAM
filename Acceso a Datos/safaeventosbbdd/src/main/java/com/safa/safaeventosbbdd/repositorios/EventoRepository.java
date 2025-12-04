package com.safa.safaeventosbbdd.repositorios;

import com.safa.safaeventosbbdd.modelos.Evento;
import com.safa.safaeventosbbdd.modelos.enums.CategoriaEventos;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface EventoRepository extends JpaRepository<Evento, Integer> {

    //Filtrar eventos por fecha y categoria
    @Query(value =
            "SELECT id, titulo, descripcion, fecha_hora, foto, ubicacion, precio, categoria, id_organizador " +
                    "FROM evento " +
                    "WHERE (:categoria IS NULL OR categoria = CAST(:categoria AS SMALLINT)) " +
                    "AND (:fechaInicio IS NULL OR fecha_hora >= :fechaInicio) " +
                    "AND (:fechaFin IS NULL OR fecha_hora <= :fechaFin)",
            nativeQuery = true)
    List<Evento> filtrarEventos(
            @Param("fechaInicio") LocalDateTime fechaInicio,
            @Param("fechaFin") LocalDateTime fechaFin,
            @Param("categoria") Integer categoria
    );

    @Query(value = " SELECT * FROM evento WHERE fecha_hora BETWEEN NOW() AND NOW() + INTERVAL '1 month'ORDER BY fecha_hora ASC",
            nativeQuery = true
    )
    List<Evento> findProximosEventos();

}

