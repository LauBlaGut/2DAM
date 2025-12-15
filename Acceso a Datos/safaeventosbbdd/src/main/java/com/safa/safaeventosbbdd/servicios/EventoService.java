package com.safa.safaeventosbbdd.servicios;

import com.safa.safaeventosbbdd.dto.EventoDTO;
import com.safa.safaeventosbbdd.exception.ElementoNoEncontradoException;
import com.safa.safaeventosbbdd.exception.EliminarNoExistenteException;
import com.safa.safaeventosbbdd.modelos.Evento;
import com.safa.safaeventosbbdd.modelos.Usuario;
import com.safa.safaeventosbbdd.modelos.enums.CategoriaEventos;
import com.safa.safaeventosbbdd.repositorios.EventoRepository;
import com.safa.safaeventosbbdd.repositorios.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
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
        List<Evento> proximos = eventoRepository.findProximosEventos();

        List<EventoDTO> dtos = new ArrayList<>();

        for (Evento e : proximos) {
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

    /**
     * Busca y devuelve un evento por su ID.
     * @param id
     * @return
     */

    public EventoDTO getById(Integer id) {

        Evento e = eventoRepository.findById(id)
                .orElseThrow(() -> new ElementoNoEncontradoException("Evento con id " +id+ " no encontrado."));

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
        Evento e = eventoRepository.findById(id)
                .orElseThrow(() -> new EliminarNoExistenteException(
                        "No se puede eliminar el evento con ID " + id + " porque no existe"));

        eventoRepository.delete(e);
    }

    //Crear evento
    public EventoDTO guardarEvento(EventoDTO dto) {

        Usuario organizador = usuarioRepository.findById(dto.getIdOrganizador())
                .orElseThrow(() -> new ElementoNoEncontradoException(
                        "No existe el organizador con ID " + dto.getIdOrganizador()));

        Evento evento = new Evento();
        evento.setTitulo(dto.getTitulo());
        evento.setDescripcion(dto.getDescripcion());
        evento.setFechaHora(LocalDateTime.of(dto.getFecha(), dto.getHora()));
        evento.setUbicacion(dto.getUbicacion());
        evento.setPrecio(dto.getPrecio());
        evento.setCategoria(dto.getCategoria());
        evento.setFoto(dto.getFoto());
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
    public List<EventoDTO> filtrarEventos(String fecha, CategoriaEventos categoria) {


        LocalDate fechaConvertida = null;

        // Validación y conversión de fecha
        if (fecha != null && !fecha.trim().isEmpty()) {
            try {
                DateTimeFormatter formatoFecha = DateTimeFormatter.ofPattern("dd/MM/yyyy");
                fechaConvertida = LocalDate.parse(fecha, formatoFecha);
            } catch (DateTimeParseException e) {
                throw new RuntimeException(
                        "Formato de fecha inválido. Use dd/MM/yyyy (ej: 30/12/2015)");
            }
        }

        List<Evento> eventos = eventoRepository.filtrarEventos(
                fechaConvertida,
                categoria != null ? categoria.ordinal() : null
        );

        // Conversión a DTO
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
    public EventoDTO editarEvento(EventoDTO dto) {

        Evento evento = eventoRepository.findById(dto.getId())
                .orElseThrow(() -> new ElementoNoEncontradoException(
                        "El evento con ID " + dto.getId() + " no existe y no puede editarse"));

        if (dto.getTitulo() != null)
            evento.setTitulo(dto.getTitulo());

        if (dto.getDescripcion() != null)
            evento.setDescripcion(dto.getDescripcion());

        if (dto.getFecha() != null && dto.getHora() != null)
            evento.setFechaHora(LocalDateTime.of(dto.getFecha(), dto.getHora()));
        else if (dto.getFecha() != null)
            evento.setFechaHora(LocalDateTime.of(dto.getFecha(), evento.getFechaHora().toLocalTime()));
        else if (dto.getHora() != null)
            evento.setFechaHora(LocalDateTime.of(evento.getFechaHora().toLocalDate(), dto.getHora()));

        if (dto.getUbicacion() != null)
            evento.setUbicacion(dto.getUbicacion());

        if (dto.getPrecio() != null)
            evento.setPrecio(dto.getPrecio());

        if (dto.getCategoria() != null)
            evento.setCategoria(dto.getCategoria());

        if (dto.getFoto() != null)
            evento.setFoto(dto.getFoto());

        // Guardamos cambios
        Evento actualizado = eventoRepository.save(evento);

        // Convertimos la entidad a DTO
        EventoDTO response = new EventoDTO();
        response.setId(actualizado.getId());
        response.setTitulo(actualizado.getTitulo());
        response.setDescripcion(actualizado.getDescripcion());
        response.setFecha(actualizado.getFechaHora().toLocalDate());
        response.setHora(actualizado.getFechaHora().toLocalTime());
        response.setUbicacion(actualizado.getUbicacion());
        response.setPrecio(actualizado.getPrecio());
        response.setCategoria(actualizado.getCategoria());
        response.setFoto(actualizado.getFoto());
        if (actualizado.getUsuario() != null)
            response.setIdOrganizador(actualizado.getUsuario().getId());

        return response;
    }
}
