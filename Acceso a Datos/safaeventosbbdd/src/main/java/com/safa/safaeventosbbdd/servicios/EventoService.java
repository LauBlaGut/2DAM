package com.safa.safaeventosbbdd.servicios;

import com.safa.safaeventosbbdd.dto.EventoDTO;
import com.safa.safaeventosbbdd.modelos.Evento;
import com.safa.safaeventosbbdd.modelos.Usuario;
import com.safa.safaeventosbbdd.modelos.enums.CategoriaEventos;
import com.safa.safaeventosbbdd.repositorios.EventoRepository;
import com.safa.safaeventosbbdd.repositorios.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class EventoService {

    private final EventoRepository eventoRepository;
    private final UsuarioRepository usuarioRepository;

    /**
     * Busca y devuelve todos los eventos de la base de datos.
     * @return
     */
    public List<EventoDTO> getAll() {

        List<Evento> eventos = eventoRepository.findAll();
        List<EventoDTO> dtos = new ArrayList<>();

        for (Evento e : eventos) {

            EventoDTO dto = new EventoDTO();
            dto.setId(e.getId());
            dto.setTitulo(e.getTitulo());
            dto.setDescripcion(e.getDescripcion());
            dto.setFecha(e.getFechaHora().toLocalDate());
            dto.setHora(e.getFechaHora().toLocalTime());
            dto.setUbicacion(e.getUbicacion());
            dto.setPrecio(e.getPrecio());
            dto.setCategoria(e.getCategoria());
            dto.setFoto(e.getFoto());
            if (e.getUsuario() != null) {
                dto.setIdOrganizador(e.getUsuario().getId());
            }

            dtos.add(dto);
        }

        return dtos;
    }

    public List<EventoDTO> getProximosEventos() {
        return eventoRepository.findProximosEventos()
                .stream()
                .map(EventoDTO::new)
                .toList();
    }

    /**
     * Busca y devuelve un evento por su ID.
     * @param id
     * @return
     */

    public EventoDTO getById(Integer id) {

        Evento e = eventoRepository.findById(id).orElse(null);

        if (e == null) {
            return null;
        }

        EventoDTO dto = new EventoDTO();
        dto.setId(e.getId());
        dto.setTitulo(e.getTitulo());
        dto.setDescripcion(e.getDescripcion());
        dto.setFecha(e.getFechaHora().toLocalDate());
        dto.setHora(e.getFechaHora().toLocalTime());
        dto.setUbicacion(e.getUbicacion());
        dto.setPrecio(e.getPrecio());
        dto.setCategoria(e.getCategoria());
        dto.setFoto(e.getFoto());

        if (e.getUsuario() != null) {
            dto.setIdOrganizador(e.getUsuario().getId());
        }

        return dto;
    }

    /**
     * Elimina un evento por su ID.
     * @param id
     * @return
     */
    public void eliminar(Integer id){
        eventoRepository.deleteById(id);
    }

     //Crear evento
    public EventoDTO guardarEvento(EventoDTO dto) {

        Evento evento = new Evento();
        evento.setTitulo(dto.getTitulo());
        evento.setDescripcion(dto.getDescripcion());
        evento.setFechaHora(LocalDateTime.of(dto.getFecha(), dto.getHora()));
        evento.setUbicacion(dto.getUbicacion());
        evento.setPrecio(dto.getPrecio());
        evento.setCategoria(dto.getCategoria());
        evento.setFoto(dto.getFoto());

        Usuario organizador = usuarioRepository.findById(dto.getIdOrganizador())
                .orElseThrow(() -> new RuntimeException("El organizador no existe"));

        evento.setUsuario(organizador);

        Evento guardado = eventoRepository.save(evento);
        EventoDTO dtoRespuesta = new EventoDTO();
        dtoRespuesta.setId(guardado.getId());
        dtoRespuesta.setTitulo(guardado.getTitulo());
        dtoRespuesta.setDescripcion(guardado.getDescripcion());
        dtoRespuesta.setFecha(guardado.getFechaHora().toLocalDate());
        dtoRespuesta.setHora(guardado.getFechaHora().toLocalTime());
        dtoRespuesta.setUbicacion(guardado.getUbicacion());
        dtoRespuesta.setPrecio(guardado.getPrecio());
        dtoRespuesta.setCategoria(guardado.getCategoria());
        dtoRespuesta.setFoto(guardado.getFoto());
        dtoRespuesta.setIdOrganizador(dto.getIdOrganizador());

        return dtoRespuesta;
    }

    //Filtrar eventos por fecha o/y categoría
    public List<EventoDTO> filtrarEventos(LocalDate fecha, CategoriaEventos categoria) {
        // La consulta SQL solo devuelve los eventos que necesitamos
        List<Evento> eventos = eventoRepository.filtrarEventos(fecha, categoria);

        List<EventoDTO> listaDTO = new ArrayList<>();

        for (Evento e : eventos) {

            EventoDTO dto = new EventoDTO();
            dto.setId(e.getId());
            dto.setTitulo(e.getTitulo());
            dto.setDescripcion(e.getDescripcion());
            if (e.getFechaHora() != null) {
                dto.setFecha(e.getFechaHora().toLocalDate());
                dto.setHora(e.getFechaHora().toLocalTime());
            }
            dto.setUbicacion(e.getUbicacion());
            dto.setPrecio(e.getPrecio());
            dto.setCategoria(e.getCategoria());
            dto.setFoto(e.getFoto());
            if (e.getUsuario() != null) {
                dto.setIdOrganizador(e.getUsuario().getId());
            }
            listaDTO.add(dto);
        }

        return listaDTO;
    }


    //Editar
    public Evento editarEvento(EventoDTO eventoDTO){
        Evento evento = eventoRepository.findById(eventoDTO.getId()).orElse(null);
        if(evento == null) return null;

        if (eventoDTO.getTitulo() != null)
            evento.setTitulo(eventoDTO.getTitulo());

        if (eventoDTO.getDescripcion() != null)
            evento.setDescripcion(eventoDTO.getDescripcion());

        if (eventoDTO.getFecha() != null && eventoDTO.getHora() != null)
            evento.setFechaHora(LocalDateTime.of(eventoDTO.getFecha(), eventoDTO.getHora()));

        if (eventoDTO.getUbicacion() != null)
            evento.setUbicacion(eventoDTO.getUbicacion());

        if (eventoDTO.getPrecio() != null)
            evento.setPrecio(eventoDTO.getPrecio());

        if (eventoDTO.getCategoria() != null)
            evento.setCategoria(eventoDTO.getCategoria());

        if (eventoDTO.getFoto() != null)
            evento.setFoto(eventoDTO.getFoto());

        return eventoRepository.save(evento);
    }


}
