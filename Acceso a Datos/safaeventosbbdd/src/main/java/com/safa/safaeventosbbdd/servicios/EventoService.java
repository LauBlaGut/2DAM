package com.safa.safaeventosbbdd.servicios;

import com.safa.safaeventosbbdd.dto.EventoDTO;
import com.safa.safaeventosbbdd.modelos.Evento;
import com.safa.safaeventosbbdd.modelos.Usuario;
import com.safa.safaeventosbbdd.repositorios.EventoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class EventoService {

    @Autowired
    private EventoRepository eventoRepository;


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
            dto.setCategoriaEventos(e.getCategoria());

            dtos.add(dto);
        }

        return dtos;
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
        dto.setTitulo(e.getTitulo());
        dto.setDescripcion(e.getDescripcion());
        dto.setFecha(e.getFechaHora().toLocalDate());
        dto.setHora(e.getFechaHora().toLocalTime());
        dto.setUbicacion(e.getUbicacion());
        dto.setPrecio(e.getPrecio());
        dto.setCategoriaEventos(e.getCategoria());

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


    public EventoDTO guardarEvento(EventoDTO dto) {
        Evento evento = new Evento();
        evento.setTitulo(dto.getTitulo());
        evento.setDescripcion(dto.getDescripcion());
        evento.setFechaHora(LocalDateTime.of(dto.getFecha(), dto.getHora()));
        evento.setUbicacion(dto.getUbicacion());
        evento.setPrecio(dto.getPrecio());
        evento.setCategoria(dto.getCategoriaEventos());

        Evento eventoGuardado = eventoRepository.save(evento);

        EventoDTO dtoGuardado = new EventoDTO();
        dtoGuardado.setTitulo(eventoGuardado.getTitulo());
        dtoGuardado.setDescripcion(eventoGuardado.getDescripcion());
        dtoGuardado.setFecha(eventoGuardado.getFechaHora().toLocalDate());
        dtoGuardado.setHora(eventoGuardado.getFechaHora().toLocalTime());
        dtoGuardado.setUbicacion(eventoGuardado.getUbicacion());
        dtoGuardado.setPrecio(eventoGuardado.getPrecio());
        dtoGuardado.setCategoriaEventos(eventoGuardado.getCategoria());

        return dtoGuardado;
    }
}
