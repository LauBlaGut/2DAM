package com.safa.safaeventosbbdd.servicios;

import com.safa.safaeventosbbdd.exception.ElementoNoEncontradoException;
import com.safa.safaeventosbbdd.exception.EliminarNoExistenteException;
import com.safa.safaeventosbbdd.repositorios.NotificacionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.safa.safaeventosbbdd.dto.NotificacionDTO;
import com.safa.safaeventosbbdd.dto.UsuarioDTO;
import com.safa.safaeventosbbdd.modelos.Notificacion;
import com.safa.safaeventosbbdd.modelos.Usuario;
import com.safa.safaeventosbbdd.repositorios.NotificacionRepository;
import com.safa.safaeventosbbdd.repositorios.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class NotificacionService {

    private final NotificacionRepository notificacionRepository;
    private final UsuarioRepository usuarioRepository;

    // Obtener todas las notificaciones
    public List<NotificacionDTO> getAll() {

        List<Notificacion> notificaciones = notificacionRepository.findAll();
        List<NotificacionDTO> dtos = new ArrayList<>();

        for (Notificacion n : notificaciones) {

            Usuario u = n.getUsuario();
            UsuarioDTO usuarioDTO = new UsuarioDTO(
                    u.getId(),
                    u.getEmail(),
                    u.getContrasenia(),
                    u.getRol(),
                    u.getVerificacion()
            );

            NotificacionDTO dto = new NotificacionDTO();
            dto.setId(n.getId());
            dto.setUsuarioDTO(usuarioDTO);
            dto.setMensaje(n.getMensaje());
            dto.setFechaEnvio(n.getFechaEnvio());
            dto.setLeido(n.isLeido());

            dtos.add(dto);
        }

        return dtos;
    }

    // Obtener notificación por ID
    public NotificacionDTO getById(Integer id) {

        Notificacion n = notificacionRepository.findById(id)
                .orElseThrow(() ->
                        new ElementoNoEncontradoException(
                                "No se encontró la notificación con ID " + id));

        Usuario u = n.getUsuario();
        UsuarioDTO usuarioDTO = new UsuarioDTO(
                u.getId(),
                u.getEmail(),
                u.getContrasenia(),
                u.getRol(),
                u.getVerificacion()
        );

        NotificacionDTO dto = new NotificacionDTO();
        dto.setId(n.getId());
        dto.setUsuarioDTO(usuarioDTO);
        dto.setMensaje(n.getMensaje());
        dto.setFechaEnvio(n.getFechaEnvio());
        dto.setLeido(n.isLeido());

        return dto;
    }

    // Guardar notificación
    public NotificacionDTO guardarNotificacion(NotificacionDTO dto) {

        Usuario u = usuarioRepository.findById(dto.getUsuarioDTO().getId())
                .orElseThrow(() ->
                        new ElementoNoEncontradoException(
                                "No se encontró el usuario con ID " + dto.getUsuarioDTO().getId()));

        Notificacion n = new Notificacion();
        n.setUsuario(u);
        n.setMensaje(dto.getMensaje());
        n.setFechaEnvio(dto.getFechaEnvio());
        n.setLeido(dto.getLeido());

        Notificacion guardada = notificacionRepository.save(n);

        NotificacionDTO respuesta = new NotificacionDTO();
        respuesta.setId(guardada.getId());
        respuesta.setUsuarioDTO(dto.getUsuarioDTO());
        respuesta.setMensaje(guardada.getMensaje());
        respuesta.setFechaEnvio(guardada.getFechaEnvio());
        respuesta.setLeido(guardada.isLeido());

        return respuesta;
    }

    // Eliminar notificación
    public void eliminar(Integer id) {

        Notificacion n = notificacionRepository.findById(id)
                .orElseThrow(() ->
                        new EliminarNoExistenteException(
                                "No se puede eliminar la notificación con ID " + id + " porque no existe"));

        notificacionRepository.delete(n);
    }
}