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
    SELECT 
        u.id,
        p.nombre,
        p.apellidos,
        pub.publicados,
        par.participados,
        (pub.publicados + par.participados) AS actividadTotal
    FROM (
        SELECT id, 0 AS dummy FROM usuario
    ) u
    LEFT JOIN perfil p ON p.id_usuario = u.id
    LEFT JOIN (
        SELECT id_organizador AS id_usuario, COUNT(*) AS publicados
        FROM evento
        GROUP BY id_organizador
    ) pub ON pub.id_usuario = u.id
    LEFT JOIN (
        SELECT id_usuario, COUNT(*) AS participados
        FROM inscripcion
        GROUP BY id_usuario
    ) par ON par.id_usuario = u.id
    ORDER BY actividadTotal DESC
    LIMIT 1
    """, nativeQuery = true)
    UsuarioActivoDTO usuarioMasActivo();
}
