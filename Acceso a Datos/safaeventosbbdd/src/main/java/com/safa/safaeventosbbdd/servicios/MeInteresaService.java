package com.safa.safaeventosbbdd.servicios;

import com.safa.safaeventosbbdd.dto.EventoDTO;
import com.safa.safaeventosbbdd.dto.MeInteresaDTO;
import com.safa.safaeventosbbdd.dto.UsuarioDTO;
import com.safa.safaeventosbbdd.modelos.Evento;
import com.safa.safaeventosbbdd.modelos.MeInteresa;
import com.safa.safaeventosbbdd.modelos.Usuario;
import com.safa.safaeventosbbdd.repositorios.EventoRepository;
import com.safa.safaeventosbbdd.repositorios.MeInteresaRepository;
import com.safa.safaeventosbbdd.repositorios.UsuarioRepository;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@Service
public class MeInteresaService {

    private MeInteresaRepository meInteresaRepository;
    private UsuarioRepository usuarioRepository;
    private EventoRepository eventoRepository;

    // Listar todos los "me interesa"
    public List<MeInteresaDTO> getAll() {
        List<MeInteresa> list = meInteresaRepository.findAll();
        List<MeInteresaDTO> dtos = new ArrayList<>();
        for (MeInteresa mi : list) {
            dtos.add(mapToDTO(mi));
        }
        return dtos;
    }

    // Listar "me interesa" de un usuario
    public List<MeInteresaDTO> getByUsuario(Integer idUsuario) {
        List<MeInteresa> list = meInteresaRepository.findByUsuario_Id(idUsuario);
        List<MeInteresaDTO> dtos = new ArrayList<>();
        for (MeInteresa mi : list) {
            dtos.add(mapToDTO(mi));
        }
        return dtos;
    }

    // Guardar "me interesa"
    public MeInteresaDTO guardarMeInteresa(MeInteresaDTO dto) {
        Usuario u = usuarioRepository.findById(dto.getUsuarioDTO().getId())
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        Evento e = eventoRepository.findById(dto.getEventoDTO().getId())
                .orElseThrow(() -> new RuntimeException("Evento no encontrado"));

        // Comprobar si ya existe
        MeInteresa existente = meInteresaRepository.findByUsuario_IdAndEvento_Id(u.getId(), e.getId());
        if (existente != null) {
            throw new RuntimeException("Ya existe un 'Me interesa' para este usuario y evento.");
        }

        MeInteresa mi = new MeInteresa();
        mi.setUsuario(u);
        mi.setEvento(e);
        mi.setFechaGuardado(dto.getFechaGuardado());

        MeInteresa guardado = meInteresaRepository.save(mi);
        return mapToDTO(guardado);
    }

    // Eliminar "me interesa"
    public void eliminar(Integer id) {
        meInteresaRepository.deleteById(id);
    }

    // Metodo privado para mapear entidad → DTO
    private MeInteresaDTO mapToDTO(MeInteresa mi) {
        Usuario u = mi.getUsuario();
        UsuarioDTO usuarioDTO = new UsuarioDTO(u.getId(), u.getEmail(), u.getContrasenia(), u.getRol(), u.getVerificacion());

        Evento e = mi.getEvento();
        EventoDTO eventoDTO = new EventoDTO();
        eventoDTO.setId(e.getId());
        eventoDTO.setTitulo(e.getTitulo());
        eventoDTO.setDescripcion(e.getDescripcion());
        eventoDTO.setFecha(e.getFechaHora().toLocalDate());
        eventoDTO.setHora(e.getFechaHora().toLocalTime());
        eventoDTO.setUbicacion(e.getUbicacion());
        eventoDTO.setPrecio(e.getPrecio());
        eventoDTO.setCategoria(e.getCategoria());

        MeInteresaDTO dto = new MeInteresaDTO();
        dto.setId(mi.getId());
        dto.setUsuarioDTO(usuarioDTO);
        dto.setEventoDTO(eventoDTO);
        dto.setFechaGuardado(mi.getFechaGuardado());

        return dto;
    }
}