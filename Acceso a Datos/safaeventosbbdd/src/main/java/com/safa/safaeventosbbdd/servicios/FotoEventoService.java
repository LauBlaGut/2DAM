package com.safa.safaeventosbbdd.servicios;

import com.safa.safaeventosbbdd.dto.*;
import com.safa.safaeventosbbdd.exception.ElementoNoEncontradoException;
import com.safa.safaeventosbbdd.exception.EliminarNoExistenteException;
import com.safa.safaeventosbbdd.modelos.Evento;
import com.safa.safaeventosbbdd.modelos.FotoEvento;
import com.safa.safaeventosbbdd.modelos.Usuario;
import com.safa.safaeventosbbdd.repositorios.EventoRepository;
import com.safa.safaeventosbbdd.repositorios.FotoEventoRepository;
import com.safa.safaeventosbbdd.repositorios.UsuarioRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@AllArgsConstructor
@Service
public class FotoEventoService {

    private final FotoEventoRepository fotoEventoRepository;
    private final UsuarioRepository usuarioRepository;
    private final EventoRepository eventoRepository;

    // Obtener todas las fotos
    public List<FotoEventoDTO> getAll() {
        List<FotoEvento> list = fotoEventoRepository.findAll();
        List<FotoEventoDTO> dtos = new ArrayList<>();

        for (FotoEvento f : list) {

            Usuario u = f.getUsuario();
            UsuarioDTO usuarioDTO = new UsuarioDTO(
                    u.getId(),
                    u.getEmail(),
                    u.getContrasenia(),
                    u.getRol(),
                    u.getVerificacion()
            );

            Evento e = f.getEvento();
            EventoDTO eventoDTO = new EventoDTO();
            eventoDTO.setId(e.getId());
            eventoDTO.setTitulo(e.getTitulo());
            eventoDTO.setDescripcion(e.getDescripcion());
            if (e.getFechaHora() != null) {
                eventoDTO.setFecha(e.getFechaHora().toLocalDate());
                eventoDTO.setHora(e.getFechaHora().toLocalTime());
            }
            eventoDTO.setUbicacion(e.getUbicacion());
            eventoDTO.setPrecio(e.getPrecio());
            eventoDTO.setCategoria(e.getCategoria());
            eventoDTO.setFoto(e.getFoto());

            FotoEventoDTO dto = new FotoEventoDTO();
            dto.setId(f.getId());
            dto.setUsuarioDTO(usuarioDTO);
            dto.setEventoDTO(eventoDTO);
            dto.setRutaFoto(f.getRutaFoto());
            dto.setFechaSubida(f.getFechaSubida());

            dtos.add(dto);
        }

        return dtos;
    }

    // Obtener fotos por evento
    public List<FotoEventoDTO> getByEvento(Integer evento_Id) {
        List<FotoEvento> list = fotoEventoRepository.findByEvento_Id(evento_Id);
        List<FotoEventoDTO> dtos = new ArrayList<>();

        for (FotoEvento f : list) {

            Usuario u = f.getUsuario();
            UsuarioDTO usuarioDTO = new UsuarioDTO(
                    u.getId(),
                    u.getEmail(),
                    u.getContrasenia(),
                    u.getRol(),
                    u.getVerificacion()
            );

            Evento e = f.getEvento();
            EventoDTO eventoDTO = new EventoDTO();
            eventoDTO.setId(e.getId());
            eventoDTO.setTitulo(e.getTitulo());
            eventoDTO.setDescripcion(e.getDescripcion());
            if (e.getFechaHora() != null) {
                eventoDTO.setFecha(e.getFechaHora().toLocalDate());
                eventoDTO.setHora(e.getFechaHora().toLocalTime());
            }
            eventoDTO.setUbicacion(e.getUbicacion());
            eventoDTO.setPrecio(e.getPrecio());
            eventoDTO.setCategoria(e.getCategoria());
            eventoDTO.setFoto(e.getFoto());

            FotoEventoDTO dto = new FotoEventoDTO();
            dto.setId(f.getId());
            dto.setUsuarioDTO(usuarioDTO);
            dto.setEventoDTO(eventoDTO);
            dto.setRutaFoto(f.getRutaFoto());
            dto.setFechaSubida(f.getFechaSubida());

            dtos.add(dto);
        }

        return dtos;
    }

    // Obtener fotos por usuario
    public List<FotoEventoDTO> getByUsuario(Integer usuario_Id) {
        List<FotoEvento> list = fotoEventoRepository.findByUsuario_Id(usuario_Id);
        List<FotoEventoDTO> dtos = new ArrayList<>();

        for (FotoEvento f : list) {

            Usuario u = f.getUsuario();
            UsuarioDTO usuarioDTO = new UsuarioDTO(
                    u.getId(),
                    u.getEmail(),
                    u.getContrasenia(),
                    u.getRol(),
                    u.getVerificacion()
            );

            Evento e = f.getEvento();
            EventoDTO eventoDTO = new EventoDTO();
            eventoDTO.setId(e.getId());
            eventoDTO.setTitulo(e.getTitulo());
            eventoDTO.setDescripcion(e.getDescripcion());
            if (e.getFechaHora() != null) {
                eventoDTO.setFecha(e.getFechaHora().toLocalDate());
                eventoDTO.setHora(e.getFechaHora().toLocalTime());
            }
            eventoDTO.setUbicacion(e.getUbicacion());
            eventoDTO.setPrecio(e.getPrecio());
            eventoDTO.setCategoria(e.getCategoria());
            eventoDTO.setFoto(e.getFoto());

            FotoEventoDTO dto = new FotoEventoDTO();
            dto.setId(f.getId());
            dto.setUsuarioDTO(usuarioDTO);
            dto.setEventoDTO(eventoDTO);
            dto.setRutaFoto(f.getRutaFoto());
            dto.setFechaSubida(f.getFechaSubida());

            dtos.add(dto);
        }

        return dtos;
    }

    // Guardar foto
    public FotoEventoDTO guardarFoto(Integer evento_Id, Integer usuario_Id, FotoEventoDTO dto) {

        Usuario usuario = usuarioRepository.findById(usuario_Id)
                .orElseThrow(() ->
                        new ElementoNoEncontradoException("No se encontró el usuario con ID " + usuario_Id));

        Evento evento = eventoRepository.findById(evento_Id)
                .orElseThrow(() ->
                        new ElementoNoEncontradoException("No se encontró el evento con ID " + evento_Id));

        FotoEvento foto = new FotoEvento();
        foto.setUsuario(usuario);
        foto.setEvento(evento);
        foto.setRutaFoto(dto.getRutaFoto());
        foto.setFechaSubida(LocalDateTime.now());

        FotoEvento guardada = fotoEventoRepository.save(foto);

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
        FotoEventoDTO respuesta = new FotoEventoDTO();
        respuesta.setId(guardada.getId());
        respuesta.setRutaFoto(guardada.getRutaFoto());
        respuesta.setUsuarioDTO(usuarioDTO);
        respuesta.setEventoDTO(eventoDTO);
        respuesta.setFechaSubida(guardada.getFechaSubida());

        return respuesta;
    }

    // Eliminar foto por ID
    public void eliminar(Integer id) {

        FotoEvento foto = fotoEventoRepository.findById(id)
                .orElseThrow(() ->
                        new EliminarNoExistenteException(
                                "No se puede eliminar la foto con ID " + id + " porque no existe"));

        fotoEventoRepository.delete(foto);
    }
}