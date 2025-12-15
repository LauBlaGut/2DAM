package com.safa.safaeventosbbdd.servicios;

import com.safa.safaeventosbbdd.dto.ComentarioEventoDTO;
import com.safa.safaeventosbbdd.dto.EventoDTO;
import com.safa.safaeventosbbdd.dto.FotoEventoDTO;
import com.safa.safaeventosbbdd.dto.UsuarioDTO;
import com.safa.safaeventosbbdd.exception.ElementoNoEncontradoException;
import com.safa.safaeventosbbdd.exception.EliminarNoExistenteException;
import com.safa.safaeventosbbdd.modelos.ComentarioEvento;
import com.safa.safaeventosbbdd.modelos.Evento;
import com.safa.safaeventosbbdd.modelos.FotoEvento;
import com.safa.safaeventosbbdd.modelos.Usuario;
import com.safa.safaeventosbbdd.repositorios.ComentarioEventoRepository;
import com.safa.safaeventosbbdd.repositorios.EventoRepository;
import com.safa.safaeventosbbdd.repositorios.UsuarioRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@AllArgsConstructor
@Service
public class ComentarioEventoService {

    private final ComentarioEventoRepository comentarioEventoRepository;
    private final UsuarioRepository usuarioRepository;
    private final EventoRepository eventoRepository;

    // Obtener todos los comentarios
    public List<ComentarioEventoDTO> getAll() {
        List<ComentarioEvento> list = comentarioEventoRepository.findAll();
        List<ComentarioEventoDTO> dtos = new ArrayList<>();

        for (ComentarioEvento c : list) {

            Usuario u = c.getUsuario();
            UsuarioDTO usuarioDTO = new UsuarioDTO(
                    u.getId(),
                    u.getEmail(),
                    u.getContrasenia(),
                    u.getRol(),
                    u.getVerificacion()
            );

            Evento e = c.getEvento();
            EventoDTO eventoDTO = new EventoDTO();
            eventoDTO.setId(e.getId());
            eventoDTO.setTitulo(e.getTitulo());
            eventoDTO.setDescripcion(e.getDescripcion());
            eventoDTO.setFecha(e.getFechaHora().toLocalDate());
            eventoDTO.setHora(e.getFechaHora().toLocalTime());
            eventoDTO.setUbicacion(e.getUbicacion());
            eventoDTO.setPrecio(e.getPrecio());
            eventoDTO.setCategoria(e.getCategoria());

            ComentarioEventoDTO dto = new ComentarioEventoDTO();
            dto.setId(c.getId());
            dto.setUsuarioDTO(usuarioDTO);
            dto.setEventoDTO(eventoDTO);
            dto.setComentario(c.getComentario());
            dto.setFechaComentario(c.getFechaComentario());

            dtos.add(dto);
        }

        return dtos;
    }

    // Obtener comentarios por evento
    public List<ComentarioEventoDTO> getByEvento(Integer idEvento) {
        List<ComentarioEvento> list = comentarioEventoRepository.findByEvento_Id(idEvento);
        List<ComentarioEventoDTO> dtos = new ArrayList<>();

        for (ComentarioEvento c : list) {

            Usuario u = c.getUsuario();
            UsuarioDTO usuarioDTO = new UsuarioDTO(
                    u.getId(),
                    u.getEmail(),
                    u.getContrasenia(),
                    u.getRol(),
                    u.getVerificacion()
            );

            Evento e = c.getEvento();
            EventoDTO eventoDTO = new EventoDTO();
            eventoDTO.setId(e.getId());
            eventoDTO.setTitulo(e.getTitulo());
            eventoDTO.setDescripcion(e.getDescripcion());
            eventoDTO.setFecha(e.getFechaHora().toLocalDate());
            eventoDTO.setHora(e.getFechaHora().toLocalTime());
            eventoDTO.setUbicacion(e.getUbicacion());
            eventoDTO.setPrecio(e.getPrecio());
            eventoDTO.setCategoria(e.getCategoria());

            ComentarioEventoDTO dto = new ComentarioEventoDTO();
            dto.setId(c.getId());
            dto.setUsuarioDTO(usuarioDTO);
            dto.setEventoDTO(eventoDTO);
            dto.setComentario(c.getComentario());
            dto.setFechaComentario(c.getFechaComentario());

            dtos.add(dto);
        }

        return dtos;
    }

    // Obtener comentarios por usuario
    public List<ComentarioEventoDTO> getByUsuario(Integer idUsuario) {
        List<ComentarioEvento> list = comentarioEventoRepository.findByUsuario_Id(idUsuario);
        List<ComentarioEventoDTO> dtos = new ArrayList<>();

        for (ComentarioEvento c : list) {

            Usuario u = c.getUsuario();
            UsuarioDTO usuarioDTO = new UsuarioDTO(
                    u.getId(),
                    u.getEmail(),
                    u.getContrasenia(),
                    u.getRol(),
                    u.getVerificacion()
            );

            Evento e = c.getEvento();
            EventoDTO eventoDTO = new EventoDTO();
            eventoDTO.setId(e.getId());
            eventoDTO.setTitulo(e.getTitulo());
            eventoDTO.setDescripcion(e.getDescripcion());
            eventoDTO.setFecha(e.getFechaHora().toLocalDate());
            eventoDTO.setHora(e.getFechaHora().toLocalTime());
            eventoDTO.setUbicacion(e.getUbicacion());
            eventoDTO.setPrecio(e.getPrecio());
            eventoDTO.setCategoria(e.getCategoria());

            ComentarioEventoDTO dto = new ComentarioEventoDTO();
            dto.setId(c.getId());
            dto.setUsuarioDTO(usuarioDTO);
            dto.setEventoDTO(eventoDTO);
            dto.setComentario(c.getComentario());
            dto.setFechaComentario(c.getFechaComentario());

            dtos.add(dto);
        }

        return dtos;
    }

    // Guardar comentario
    public ComentarioEventoDTO guardarComentario(Integer evento_Id, Integer usuario_Id, ComentarioEventoDTO dto) {

        Usuario usuario = usuarioRepository.findById(usuario_Id)
                .orElseThrow(() -> new ElementoNoEncontradoException(
                        "No se encontró el usuario con ID " + usuario_Id));

        Evento evento = eventoRepository.findById(evento_Id)
                .orElseThrow(() -> new ElementoNoEncontradoException(
                        "No se encontró el evento con ID " + evento_Id));

        ComentarioEvento comentario = new ComentarioEvento();
        comentario.setUsuario(usuario);
        comentario.setEvento(evento);
        comentario.setComentario(dto.getComentario());
        comentario.setFechaComentario(LocalDateTime.now());

        ComentarioEvento guardado = comentarioEventoRepository.save(comentario);

        // Construir usuarioDTO
        UsuarioDTO usuarioDTO = new UsuarioDTO();
        usuarioDTO.setId(usuario.getId());
        usuarioDTO.setEmail(usuario.getEmail());
        usuarioDTO.setContrasenia(null);
        usuarioDTO.setRolUsuario(usuario.getRol());
        usuarioDTO.setVerificacion(usuario.getVerificacion());

        // Construir eventoDTO
        EventoDTO eventoDTO = new EventoDTO();
        eventoDTO.setId(evento.getId());
        eventoDTO.setTitulo(evento.getTitulo());
        eventoDTO.setDescripcion(evento.getDescripcion());
        if (evento.getFechaHora() != null) {
            eventoDTO.setFecha(evento.getFechaHora().toLocalDate());
            eventoDTO.setHora(evento.getFechaHora().toLocalTime());
        }
        eventoDTO.setUbicacion(evento.getUbicacion());
        eventoDTO.setPrecio(evento.getPrecio());
        eventoDTO.setCategoria(evento.getCategoria());
        eventoDTO.setFoto(evento.getFoto());
        eventoDTO.setIdOrganizador(evento.getUsuario().getId());

        // Construir respuesta
        ComentarioEventoDTO respuesta = new ComentarioEventoDTO();
        respuesta.setId(guardado.getId());
        respuesta.setComentario(guardado.getComentario());
        respuesta.setUsuarioDTO(usuarioDTO);
        respuesta.setEventoDTO(eventoDTO);
        respuesta.setFechaComentario(guardado.getFechaComentario());

        return respuesta;
    }

    // Eliminar comentario por ID
    public void eliminar(Integer id) {

        ComentarioEvento comentario = comentarioEventoRepository.findById(id)
                .orElseThrow(() -> new EliminarNoExistenteException(
                        "No se puede eliminar el comentario con ID " + id + " porque no existe"));

        comentarioEventoRepository.delete(comentario);
    }

    public Usuario loadUserByUsername(String email) throws UsernameNotFoundException {
        return usuarioRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado: " + email));
    }
}