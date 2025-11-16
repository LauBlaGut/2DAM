package com.safa.safaeventosbbdd.servicios;

import com.safa.safaeventosbbdd.dto.ComentarioEventoDTO;
import com.safa.safaeventosbbdd.dto.EventoDTO;
import com.safa.safaeventosbbdd.dto.UsuarioDTO;
import com.safa.safaeventosbbdd.modelos.ComentarioEvento;
import com.safa.safaeventosbbdd.modelos.Evento;
import com.safa.safaeventosbbdd.modelos.Usuario;
import com.safa.safaeventosbbdd.repositorios.ComentarioEventoRepository;
import com.safa.safaeventosbbdd.repositorios.EventoRepository;
import com.safa.safaeventosbbdd.repositorios.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class ComentarioEventoService {

    @Autowired
    private ComentarioEventoRepository comentarioEventoRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private EventoRepository eventoRepository;

    // Obtener todos los comentarios
    public List<ComentarioEventoDTO> getAll() {
        List<ComentarioEvento> list = comentarioEventoRepository.findAll();
        List<ComentarioEventoDTO> dtos = new ArrayList<>();
        for (ComentarioEvento c : list) {
            dtos.add(mapToDTO(c));
        }
        return dtos;
    }

    // Obtener comentarios por evento
    public List<ComentarioEventoDTO> getByEvento(Integer idEvento) {
        List<ComentarioEvento> list = comentarioEventoRepository.findByIdEvento_Id(idEvento);
        List<ComentarioEventoDTO> dtos = new ArrayList<>();
        for (ComentarioEvento c : list) {
            dtos.add(mapToDTO(c));
        }
        return dtos;
    }

    // Obtener comentarios por usuario
    public List<ComentarioEventoDTO> getByUsuario(Integer idUsuario) {
        List<ComentarioEvento> list = comentarioEventoRepository.findByIdUsuario_Id(idUsuario);
        List<ComentarioEventoDTO> dtos = new ArrayList<>();
        for (ComentarioEvento c : list) {
            dtos.add(mapToDTO(c));
        }
        return dtos;
    }

    // Guardar comentario
    public ComentarioEventoDTO guardarComentario(ComentarioEventoDTO dto) {
        Usuario u = usuarioRepository.findById(dto.getUsuarioDTO().getId())
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        Evento e = eventoRepository.findById(dto.getEventoDTO().getId())
                .orElseThrow(() -> new RuntimeException("Evento no encontrado"));

        ComentarioEvento c = new ComentarioEvento();
        c.setIdUsuario(u);
        c.setIdEvento(e);
        c.setComentario(dto.getComentario());
        c.setFechaComentario(dto.getFechaComentario() != null ? dto.getFechaComentario() : new Date());

        ComentarioEvento guardado = comentarioEventoRepository.save(c);
        return mapToDTO(guardado);
    }

    // Eliminar comentario por ID
    public void eliminar(Integer id) {
        comentarioEventoRepository.deleteById(id);
    }

    // --- Mapeo entidad → DTO ---
    private ComentarioEventoDTO mapToDTO(ComentarioEvento c) {
        Usuario u = c.getIdUsuario();
        UsuarioDTO usuarioDTO = new UsuarioDTO(u.getId(), u.getEmail(), u.getContrasenia(), u.getRol(), u.getVerificacion());

        Evento e = c.getIdEvento();
        EventoDTO eventoDTO = new EventoDTO();
        eventoDTO.setId(e.getId());
        eventoDTO.setTitulo(e.getTitulo());
        eventoDTO.setDescripcion(e.getDescripcion());
        eventoDTO.setFecha(e.getFechaHora().toLocalDate());
        eventoDTO.setHora(e.getFechaHora().toLocalTime());
        eventoDTO.setUbicacion(e.getUbicacion());
        eventoDTO.setPrecio(e.getPrecio());
        eventoDTO.setCategoriaEventos(e.getCategoria());

        ComentarioEventoDTO dto = new ComentarioEventoDTO();
        dto.setId(c.getId());
        dto.setUsuarioDTO(usuarioDTO);
        dto.setEventoDTO(eventoDTO);
        dto.setComentario(c.getComentario());
        dto.setFechaComentario(c.getFechaComentario());

        return dto;
    }
}