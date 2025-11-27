package com.safa.safaeventosbbdd.repositorios;

import com.safa.safaeventosbbdd.modelos.Notificacion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NotificacionRepository extends JpaRepository <Notificacion, Integer>{
    List<Notificacion> findByUsuario_Id(Integer usuario);
    List<Notificacion> findByUsuario_IdAndLeidoFalse(Integer usuario);
}
