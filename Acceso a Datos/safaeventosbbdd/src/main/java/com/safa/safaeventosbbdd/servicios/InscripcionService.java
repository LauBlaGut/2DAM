package com.safa.safaeventosbbdd.servicios;

import com.safa.safaeventosbbdd.dto.EventoDTO;
import com.safa.safaeventosbbdd.dto.EventoTopDTO;
import com.safa.safaeventosbbdd.dto.InscripcionDTO;
import com.safa.safaeventosbbdd.dto.UsuarioDTO;
import com.safa.safaeventosbbdd.exception.ElementoNoEncontradoException;
import com.safa.safaeventosbbdd.modelos.Evento;
import com.safa.safaeventosbbdd.modelos.Inscripcion;
import com.safa.safaeventosbbdd.modelos.Usuario;
import com.safa.safaeventosbbdd.modelos.enums.MetodoPago;
import com.safa.safaeventosbbdd.repositorios.EventoRepository;
import com.safa.safaeventosbbdd.repositorios.InscripcionRepository;
import com.safa.safaeventosbbdd.repositorios.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
public class InscripcionService {

    private final InscripcionRepository inscripcionRepository;
    private final UsuarioRepository usuarioRepository;
    private final EventoRepository eventoRepository;

    // Listar todas las inscripciones
    public List<InscripcionDTO> getAll() {
        List<Inscripcion> list = inscripcionRepository.findAll();
        List<InscripcionDTO> dtos = new ArrayList<>();
        for (Inscripcion i : list) {
            dtos.add(mapToDTO(i));
        }
        return dtos;
    }

    // Listar inscripciones por usuario
    public List<EventoDTO> getEventos_Usuario(Integer usuario_Id) {
        List<Inscripcion> inscripciones = inscripcionRepository.findByUsuario_Id(usuario_Id);

        List<EventoDTO> eventos = new ArrayList<>();

        for (Inscripcion i : inscripciones) {

            Evento evento = i.getEvento();
            EventoDTO dto = new EventoDTO();

            dto.setId(evento.getId());
            dto.setTitulo(evento.getTitulo());
            dto.setDescripcion(evento.getDescripcion());

            if (evento.getFechaHora() != null) {
                dto.setFecha(evento.getFechaHora().toLocalDate());
                dto.setHora(evento.getFechaHora().toLocalTime());
            }

            dto.setUbicacion(evento.getUbicacion());
            dto.setPrecio(evento.getPrecio());
            dto.setCategoria(evento.getCategoria());

            eventos.add(dto);
        }

        return eventos;
    }

    // Listar inscripciones por evento
    public List<InscripcionDTO> getByEvento(Integer evento_Id) {
        List<Inscripcion> list = inscripcionRepository.findByEvento_Id(evento_Id);
        List<InscripcionDTO> dtos = new ArrayList<>();
        for (Inscripcion i : list) {
            dtos.add(mapToDTO(i));
        }
        return dtos;
    }


    public InscripcionDTO inscribirUsuarioAEvento(Integer usuario_Id, Integer evento_Id, MetodoPago metodoPago) {

        Usuario usuario = usuarioRepository.findById(usuario_Id)
                .orElseThrow(() ->
                        new ElementoNoEncontradoException("No se encontró el usuario con ID " + usuario_Id));

        Evento evento = eventoRepository.findById(evento_Id)
                .orElseThrow(() ->
                        new ElementoNoEncontradoException("No se encontró el evento con ID " + evento_Id));

        // Evitar inscripciones duplicadas
        if (inscripcionRepository.findByUsuario_IdAndEvento_Id(usuario_Id, evento_Id) != null) {
            throw new IllegalStateException("El usuario ya está inscrito en este evento.");
        }

        boolean esGratis = evento.getPrecio().doubleValue() == 0;

        Inscripcion inscripcion = new Inscripcion();
        inscripcion.setUsuario(usuario);
        inscripcion.setEvento(evento);
        inscripcion.setMetodoPago(metodoPago);
        inscripcion.setTieneCoste(!esGratis);

        inscripcion.setPagoRealizado(esGratis);

        Inscripcion guardada = inscripcionRepository.save(inscripcion);

        return mapToDTO(guardada);
    }

    public boolean estaInscrito(Integer usuario_Id, Integer evento_Id) {
        return inscripcionRepository.findByUsuario_IdAndEvento_Id(usuario_Id, evento_Id) != null;
    }

    public InscripcionDTO pagarInscripcion(Integer inscripcion_Id) {

        Inscripcion inscripcion = inscripcionRepository.findById(inscripcion_Id)
                .orElseThrow(() -> new RuntimeException("Inscripción no encontrada"));

        // Si ya está pagada
        if (inscripcion.isPagoRealizado()) {
            throw new IllegalStateException("Esta inscripción ya está pagada.");
        }

        // Marcar como pagada
        inscripcion.setPagoRealizado(true);

        Inscripcion guardada = inscripcionRepository.save(inscripcion);

        return mapToDTO(guardada);
    }

    // Eliminar inscripcion
    public void eliminar(Integer id) {
        inscripcionRepository.deleteById(id);
    }

    // --- Mapear entidad a DTO
    private InscripcionDTO mapToDTO(Inscripcion i) {
        Usuario u = i.getUsuario();
        UsuarioDTO usuarioDTO = new UsuarioDTO(u.getId(), u.getEmail(), u.getContrasenia(), u.getRol(), u.getVerificacion());

        Evento e = i.getEvento();
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

        // DTO final
        InscripcionDTO dto = new InscripcionDTO();
        dto.setId(i.getId());
        dto.setUsuarioDTO(usuarioDTO);
        dto.setEventoDTO(eventoDTO);
        dto.setPagoRealizado(i.isPagoRealizado());
        dto.setMetodoPago(i.getMetodoPago());
        dto.setTieneCoste(i.isTieneCoste());


        return dto;
    }

    //top 5 de eventos con más inscripciones
    public List<EventoTopDTO> obtenerTop5Eventos() {
        return inscripcionRepository.top5Eventos();
    }
}