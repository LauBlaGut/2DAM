package com.safa.safaeventosbbdd.repositorios;

import com.safa.safaeventosbbdd.dto.UsuarioActivoDTO;
import com.safa.safaeventosbbdd.modelos.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario,Integer> {
    Optional<Usuario> findByEmail(String email);

    @Query(value = """
    WITH eventos_publicados AS (
            SELECT
                id_organizador AS id_usuario,
                COUNT(*) AS eventos_publicados
            FROM safaeventos.evento
            GROUP BY id_organizador
        ),
        eventos_participados AS (
            SELECT
                id_usuario,
                COUNT(*) AS eventos_participados
            FROM safaeventos.inscripcion
            GROUP BY id_usuario
        ),
        usuario_actividad AS (
            SELECT
                u.id,
                u.email,
                p.nombre,
                p.apellidos,
                COALESCE(ep.eventos_publicados, 0) AS eventos_publicados,
                COALESCE(epar.eventos_participados, 0) AS eventos_participados,
                (COALESCE(ep.eventos_publicados, 0) + 
                 COALESCE(epar.eventos_participados, 0)) AS total_actividad
            FROM safaeventos.usuario u
            LEFT JOIN safaeventos.perfil p ON u.id = p.id_usuario
            LEFT JOIN eventos_publicados ep ON u.id = ep.id_usuario
            LEFT JOIN eventos_participados epar ON u.id = epar.id_usuario
        )
        SELECT 
            id,
            email,
            nombre,
            apellidos,
            eventos_publicados,
            eventos_participados,
            total_actividad
        FROM usuario_actividad
        WHERE total_actividad > 0
        ORDER BY total_actividad DESC,
                 eventos_publicados DESC,
                 eventos_participados DESC
        LIMIT 1
        """, nativeQuery = true)
    UsuarioActivoDTO usuarioMasActivo();
}