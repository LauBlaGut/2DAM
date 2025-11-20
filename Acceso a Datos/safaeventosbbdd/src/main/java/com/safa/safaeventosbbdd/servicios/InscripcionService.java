package com.safa.safaeventosbbdd.servicios;

import com.safa.safaeventosbbdd.dto.EventoDTO;
import com.safa.safaeventosbbdd.dto.InscripcionDTO;
import com.safa.safaeventosbbdd.dto.UsuarioDTO;
import com.safa.safaeventosbbdd.modelos.Evento;
import com.safa.safaeventosbbdd.modelos.Inscripcion;
import com.safa.safaeventosbbdd.modelos.Usuario;
import com.safa.safaeventosbbdd.repositorios.EventoRepository;
import com.safa.safaeventosbbdd.repositorios.InscripcionRepository;
import com.safa.safaeventosbbdd.repositorios.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class InscripcionService {

    @Autowired
    private InscripcionRepository inscripcionRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private EventoRepository eventoRepository;

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
    public List<InscripcionDTO> getByUsuario(Integer idUsuario) {
        List<Inscripcion> list = inscripcionRepository.findByIdUsuario_Id(idUsuario);
        List<InscripcionDTO> dtos = new ArrayList<>();
        for (Inscripcion i : list) {
            dtos.add(mapToDTO(i));
        }
        return dtos;
    }

    // Listar inscripciones por evento
    public List<InscripcionDTO> getByEvento(Integer idEvento) {
        List<Inscripcion> list = inscripcionRepository.findByIdEvento_Id(idEvento);
        List<InscripcionDTO> dtos = new ArrayList<>();
        for (Inscripcion i : list) {
            dtos.add(mapToDTO(i));
        }
        return dtos;
    }

    // Guardar inscripcion
    public InscripcionDTO guardarInscripcion(InscripcionDTO dto) {
        Usuario u = usuarioRepository.findById(dto.getUsuarioDTO().getId())
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        Evento e = eventoRepository.findById(dto.getEventoDTO().getId())
                .orElseThrow(() -> new RuntimeException("Evento no encontrado"));

        // Comprobar duplicado
        Inscripcion existente = inscripcionRepository.findByIdUsuario_IdAndIdEvento_Id(u.getId(), e.getId());
        if (existente != null) {
            throw new RuntimeException("Ya existe una inscripción para este usuario y evento.");
        }

        Inscripcion inscripcion = new Inscripcion();
        inscripcion.setIdUsuario(u);
        inscripcion.setIdEvento(e);

        inscripcion.setPagoRealizado(
                dto.getPagoRealizado() != null ? dto.getPagoRealizado() : false
        );

        inscripcion.setTieneCoste(
                dto.getTieneCoste() != null ? dto.getTieneCoste() : false
        );

        inscripcion.setMetodoPago(dto.getMetodoPago());

        Inscripcion guardada = inscripcionRepository.save(inscripcion);
        return mapToDTO(guardada);
    }

    // Eliminar inscripcion
    public void eliminar(Integer id) {
        inscripcionRepository.deleteById(id);
    }

    // --- Mapear entidad → DTO ---
    private InscripcionDTO mapToDTO(Inscripcion i) {
        Usuario u = i.getIdUsuario();
        UsuarioDTO usuarioDTO = new UsuarioDTO(u.getId(), u.getEmail(), u.getContrasenia(), u.getRol(), u.getVerificacion());

        Evento e = i.getIdEvento();
        EventoDTO eventoDTO = new EventoDTO();
        eventoDTO.setId(e.getId());
        eventoDTO.setTitulo(e.getTitulo());
        eventoDTO.setDescripcion(e.getDescripcion());

        // OJO: si getFechaHora() puede ser null → comprobamos
        if (e.getFechaHora() != null) {
            eventoDTO.setFecha(e.getFechaHora().toLocalDate());
            eventoDTO.setHora(e.getFechaHora().toLocalTime());
        }

        eventoDTO.setUbicacion(e.getUbicacion());
        eventoDTO.setPrecio(e.getPrecio());
        eventoDTO.setCategoriaEventos(e.getCategoria());

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
}