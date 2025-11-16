package com.safa.safaeventosbbdd.repositorios;

import com.safa.safaeventosbbdd.modelos.Notificacion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NotificacionRepository extends JpaRepository <Notificacion, Integer>{
    List<Notificacion> findByIdUsuario_Id(Integer idUsuario);
    List<Notificacion> findByIdUsuario_IdAndLeidoFalse(Integer idUsuario);
}
