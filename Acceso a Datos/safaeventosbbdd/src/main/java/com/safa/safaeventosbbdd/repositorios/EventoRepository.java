package com.safa.safaeventosbbdd.repositorios;

import com.safa.safaeventosbbdd.modelos.Evento;
import com.safa.safaeventosbbdd.modelos.enums.CategoriaEventos;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface EventoRepository extends JpaRepository<Evento, Integer> {

    @Query("SELECT e FROM Evento e " +
            "WHERE (:fecha IS NULL OR " +
            "       (EXTRACT(YEAR FROM e.fechaHora) = EXTRACT(YEAR FROM :fecha) AND " +
            "        EXTRACT(MONTH FROM e.fechaHora) = EXTRACT(MONTH FROM :fecha) AND " +
            "        EXTRACT(DAY FROM e.fechaHora) = EXTRACT(DAY FROM :fecha))) " +
            "AND (:categoria IS NULL OR e.categoria = :categoria)")
    List<Evento> filtrarEventos(
            @Param("fecha") LocalDate fecha,
            @Param("categoria") CategoriaEventos categoria
    );

    @Query(value = " SELECT * FROM evento WHERE fecha_hora BETWEEN NOW() AND NOW() + INTERVAL '1 month'ORDER BY fecha_hora ASC",
            nativeQuery = true
    )
    List<Evento> findProximosEventos();

}

