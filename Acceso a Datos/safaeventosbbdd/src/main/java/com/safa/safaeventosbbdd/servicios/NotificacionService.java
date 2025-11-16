package com.safa.safaeventosbbdd.servicios;

import com.safa.safaeventosbbdd.repositorios.NotificacionRepository;
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
public class NotificacionService {

    @Autowired
    private NotificacionRepository notificacionRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    // Obtener todas las notificaciones
    public List<NotificacionDTO> getAll() {
        List<Notificacion> notificaciones = notificacionRepository.findAll();
        List<NotificacionDTO> dtos = new ArrayList<>();

        for (Notificacion n : notificaciones) {
            Usuario u = n.getIdUsuario();
            UsuarioDTO usuarioDTO = new UsuarioDTO(u.getId(), u.getEmail(), u.getContrasenia(), u.getRol(), u.getVerificacion());
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
        Notificacion n = notificacionRepository.findById(id).orElse(null);
        if (n == null) return null;

        Usuario u = n.getIdUsuario();
        UsuarioDTO usuarioDTO = new UsuarioDTO(u.getId(), u.getEmail(), u.getContrasenia(), u.getRol(), u.getVerificacion());

        NotificacionDTO dto = new NotificacionDTO();
        dto.setId(n.getId());
        dto.setUsuarioDTO(usuarioDTO);
        dto.setMensaje(n.getMensaje());
        dto.setFechaEnvio(n.getFechaEnvio());
        dto.setLeido(n.isLeido());

        return dto;
    }

    // Guardar o actualizar notificación
    public NotificacionDTO guardarNotificacion(NotificacionDTO dto) {
        Notificacion n = new Notificacion();

        Usuario u = usuarioRepository.findById(dto.getUsuarioDTO().getId())
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado con id: " + dto.getUsuarioDTO().getId()));

        n.setIdUsuario(u);
        n.setMensaje(dto.getMensaje());
        n.setFechaEnvio(dto.getFechaEnvio());
        n.setLeido(dto.getLeido());

        Notificacion guardada = notificacionRepository.save(n);

        NotificacionDTO dtoGuardado = new NotificacionDTO();
        dtoGuardado.setId(guardada.getId());
        dtoGuardado.setUsuarioDTO(dto.getUsuarioDTO());
        dtoGuardado.setMensaje(guardada.getMensaje());
        dtoGuardado.setFechaEnvio(guardada.getFechaEnvio());
        dtoGuardado.setLeido(guardada.isLeido());

        return dtoGuardado;
    }

    // Eliminar notificación
    public void eliminar(Integer id) {
        notificacionRepository.deleteById(id);
    }

    // --- Metodo privado para mapear Notificacion → NotificacionDTO ---
    private NotificacionDTO mapToDTO(Notificacion n) {
        Usuario u = n.getIdUsuario();
        UsuarioDTO usuarioDTO = new UsuarioDTO(u.getId(), u.getEmail(), u.getContrasenia(), u.getRol(), u.getVerificacion());
        NotificacionDTO dto = new NotificacionDTO();
        dto.setId(n.getId());
        dto.setUsuarioDTO(usuarioDTO);
        dto.setMensaje(n.getMensaje());
        dto.setFechaEnvio(n.getFechaEnvio());
        dto.setLeido(n.isLeido());

        return dto;
    }
}
