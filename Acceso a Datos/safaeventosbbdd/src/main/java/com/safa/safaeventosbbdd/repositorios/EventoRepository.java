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

    @Query(value = "SELECT * FROM safaeventos.evento e " +
            "WHERE (CAST(:fecha AS DATE) IS NULL OR DATE(e.fecha_hora) = CAST(:fecha AS DATE)) " +
            "AND (CAST(:categoria AS INTEGER) IS NULL OR e.categoria = CAST(:categoria AS INTEGER))",
            nativeQuery = true)
    List<Evento> filtrarEventos(
            @Param("fecha") LocalDate fecha,
            @Param("categoria") Integer categoria
    );

    @Query(
            value = "SELECT * FROM safaeventos.evento e WHERE e.fecha_hora >= CURRENT_DATE ORDER BY e.fecha_hora ASC",
            nativeQuery = true
    )
    List<Evento> findProximosEventos();

}
