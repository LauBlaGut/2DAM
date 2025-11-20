package com.safa.safaeventosbbdd.servicios;

import com.safa.safaeventosbbdd.dto.EventoDTO;
import com.safa.safaeventosbbdd.dto.FotoEventoDTO;
import com.safa.safaeventosbbdd.dto.UsuarioDTO;
import com.safa.safaeventosbbdd.modelos.Evento;
import com.safa.safaeventosbbdd.modelos.FotoEvento;
import com.safa.safaeventosbbdd.modelos.Usuario;
import com.safa.safaeventosbbdd.repositorios.EventoRepository;
import com.safa.safaeventosbbdd.repositorios.FotoEventoRepository;
import com.safa.safaeventosbbdd.repositorios.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class FotoEventoService {

    @Autowired
    private FotoEventoRepository fotoEventoRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private EventoRepository eventoRepository;

    // Obtener todas las fotos
    public List<FotoEventoDTO> getAll() {
        List<FotoEvento> list = fotoEventoRepository.findAll();
        List<FotoEventoDTO> dtos = new ArrayList<>();
        for (FotoEvento f : list) {
            dtos.add(mapToDTO(f));
        }
        return dtos;
    }

    // Obtener fotos por evento
    public List<FotoEventoDTO> getByEvento(Integer idEvento) {
        List<FotoEvento> list = fotoEventoRepository.findByIdEvento_Id(idEvento);
        List<FotoEventoDTO> dtos = new ArrayList<>();
        for (FotoEvento f : list) {
            dtos.add(mapToDTO(f));
        }
        return dtos;
    }

    // Obtener fotos por usuario
    public List<FotoEventoDTO> getByUsuario(Integer idUsuario) {
        List<FotoEvento> list = fotoEventoRepository.findByIdUsuario_Id(idUsuario);
        List<FotoEventoDTO> dtos = new ArrayList<>();
        for (FotoEvento f : list) {
            dtos.add(mapToDTO(f));
        }
        return dtos;
    }

    // Guardar foto
    public FotoEventoDTO guardarFoto(FotoEventoDTO dto) {
        Usuario u = usuarioRepository.findById(dto.getUsuarioDTO().getId())
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        Evento e = eventoRepository.findById(dto.getEventoDTO().getId())
                .orElseThrow(() -> new RuntimeException("Evento no encontrado"));

        FotoEvento f = new FotoEvento();
        f.setIdUsuario(u);
        f.setIdEvento(e);
        f.setRutaFoto(dto.getRutaFoto());
        f.setFechaSubida(dto.getFechaSubida() != null ? dto.getFechaSubida() : new Date());

        FotoEvento guardada = fotoEventoRepository.save(f);
        return mapToDTO(guardada);
    }

    // Eliminar foto por ID
    public void eliminar(Integer id) {
        fotoEventoRepository.deleteById(id);
    }

    // --- Mapeo entidad → DTO ---
    private FotoEventoDTO mapToDTO(FotoEvento f) {
        Usuario u = f.getIdUsuario();
        UsuarioDTO usuarioDTO = new UsuarioDTO(u.getId(), u.getEmail(), u.getContrasenia(), u.getRol(), u.getVerificacion());

        Evento e = f.getIdEvento();
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
        eventoDTO.setCategoriaEventos(e.getCategoria());

        FotoEventoDTO dto = new FotoEventoDTO();
        dto.setId(f.getId());
        dto.setUsuarioDTO(usuarioDTO);
        dto.setEventoDTO(eventoDTO);
        dto.setRutaFoto(f.getRutaFoto());
        dto.setFechaSubida(f.getFechaSubida());

        return dto;
    }
}