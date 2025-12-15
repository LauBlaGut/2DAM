package com.safa.safaeventosbbdd.servicios;

import com.safa.safaeventosbbdd.dto.PerfilDTO;
import com.safa.safaeventosbbdd.dto.UsuarioDTO;
import com.safa.safaeventosbbdd.exception.ElementoNoEncontradoException;
import com.safa.safaeventosbbdd.exception.EliminarNoExistenteException;
import com.safa.safaeventosbbdd.modelos.Perfil;
import com.safa.safaeventosbbdd.modelos.Usuario;
import com.safa.safaeventosbbdd.repositorios.PerfilRepository;
import com.safa.safaeventosbbdd.repositorios.UsuarioRepository;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RequiredArgsConstructor
@Service
public class PerfilService {

    private final PerfilRepository perfilRepository;
    private final UsuarioRepository usuarioRepository;

    // Obtener todos los perfiles
    public List<PerfilDTO> getAll() {

        List<Perfil> perfiles = perfilRepository.findAll();
        List<PerfilDTO> dtos = new ArrayList<>();

        for (Perfil p : perfiles) {

            PerfilDTO dto = new PerfilDTO();
            dto.setId(p.getId());
            dto.setIdUsuario(p.getUsuario().getId());
            dto.setNombre(p.getNombre());
            dto.setApellidos(p.getApellidos());
            dto.setCurso(p.getCurso());
            dto.setFotoURL(p.getFotoURL());

            if (p.getFechaRegistro() != null) {
                dto.setFechaRegistro(
                        Date.from(p.getFechaRegistro().atStartOfDay(ZoneId.systemDefault()).toInstant())
                );
            }

            dtos.add(dto);
        }

        return dtos;
    }

    // Obtener perfil por ID
    public PerfilDTO getById(Integer id) {

        Perfil p = perfilRepository.findById(id)
                .orElseThrow(() ->
                        new ElementoNoEncontradoException(
                                "No se encontró el perfil con ID " + id));

        PerfilDTO dto = new PerfilDTO();
        dto.setId(p.getId());
        dto.setIdUsuario(p.getUsuario().getId());
        dto.setNombre(p.getNombre());
        dto.setApellidos(p.getApellidos());
        dto.setCurso(p.getCurso());
        dto.setFotoURL(p.getFotoURL());

        if (p.getFechaRegistro() != null) {
            dto.setFechaRegistro(
                    Date.from(p.getFechaRegistro().atStartOfDay(ZoneId.systemDefault()).toInstant())
            );
        }

        return dto;
    }

    // Guardar o actualizar un perfil
    public PerfilDTO guardarPerfil(PerfilDTO dto) {

        Usuario usuario = usuarioRepository.findById(dto.getIdUsuario())
                .orElseThrow(() ->
                        new ElementoNoEncontradoException(
                                "No se encontró el usuario con ID " + dto.getIdUsuario()));

        Perfil perfil = new Perfil();
        perfil.setUsuario(usuario);
        perfil.setNombre(dto.getNombre());
        perfil.setApellidos(dto.getApellidos());
        perfil.setCurso(dto.getCurso());
        perfil.setFotoURL(dto.getFotoURL());

        if (dto.getFechaRegistro() != null) {
            perfil.setFechaRegistro(
                    dto.getFechaRegistro().toInstant().atZone(ZoneId.systemDefault()).toLocalDate()
            );
        } else {
            perfil.setFechaRegistro(LocalDate.now());
        }

        Perfil guardado = perfilRepository.save(perfil);

        PerfilDTO respuesta = new PerfilDTO();
        respuesta.setId(guardado.getId());
        respuesta.setIdUsuario(usuario.getId());
        respuesta.setNombre(guardado.getNombre());
        respuesta.setApellidos(guardado.getApellidos());
        respuesta.setCurso(guardado.getCurso());
        respuesta.setFotoURL(guardado.getFotoURL());

        respuesta.setFechaRegistro(
                Date.from(guardado.getFechaRegistro().atStartOfDay(ZoneId.systemDefault()).toInstant())
        );

        return respuesta;
    }

    // Eliminar perfil
    public void eliminar(Integer id) {

        Perfil perfil = perfilRepository.findById(id)
                .orElseThrow(() ->
                        new EliminarNoExistenteException(
                                "No se puede eliminar el perfil con ID " + id + " porque no existe"));

        perfilRepository.delete(perfil);
    }
}