package com.safa.safaeventosbbdd.repositorios;

import com.safa.safaeventosbbdd.dto.EventoDTO;
import com.safa.safaeventosbbdd.dto.EventoTopDTO;
import com.safa.safaeventosbbdd.modelos.Inscripcion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.data.repository.query.Param;

import java.util.List;

@Repository
public interface InscripcionRepository extends JpaRepository <Inscripcion, Integer>{
    //List<Inscripcion> findByUsuario_Id(Integer usuario);

    @Query("SELECT i FROM Inscripcion i JOIN FETCH i.evento WHERE i.usuario.id = :usuario")
    List<Inscripcion> findByUsuario_Id(@Param("usuario") Integer usuario);

    List<Inscripcion> findByEvento_Id(Integer evento);

    Inscripcion findByUsuario_IdAndEvento_Id(Integer usuario, Integer evento);

    @Query(value = """
        SELECT
            e.id AS id,
            e.titulo AS titulo,
            e.descripcion AS descripcion,
            e.fecha_hora AS fechaHora,
            e.ubicacion AS ubicacion,
            e.precio AS precio,
            e.foto AS foto,
            e.categoria AS categoria,
            COUNT(i.id) AS asistentes
            FROM safaeventos.inscripcion i
            JOIN safaeventos.evento e ON e.id = i.id_evento
            GROUP BY e.id
            ORDER BY asistentes DESC
            LIMIT 5
                    """, nativeQuery = true)
    List<EventoTopDTO> top5Eventos();
}
