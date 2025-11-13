package com.safa.safaeventosbd.repositorios;

import com.safa.safaeventosbd.modelos.Notificacion;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface NotificacionRepository extends JpaRepository<Notificacion, Long> {
    List<Notificacion> findByUsuario_Id(Long idUsuario);

    List<Notificacion> findByUsuario_IdAndLeidoFalse(Long idUsuario);
}
