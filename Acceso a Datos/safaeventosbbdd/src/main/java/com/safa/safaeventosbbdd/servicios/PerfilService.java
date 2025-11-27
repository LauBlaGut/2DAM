package com.safa.safaeventosbbdd.servicios;

import com.safa.safaeventosbbdd.dto.PerfilDTO;
import com.safa.safaeventosbbdd.dto.UsuarioDTO;
import com.safa.safaeventosbbdd.modelos.Perfil;
import com.safa.safaeventosbbdd.modelos.Usuario;
import com.safa.safaeventosbbdd.repositorios.PerfilRepository;
import com.safa.safaeventosbbdd.repositorios.UsuarioRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@AllArgsConstructor
@Service
public class PerfilService {

    private PerfilRepository perfilRepository;
    private UsuarioRepository usuarioRepository;

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
                dto.setFechaRegistro(Date.from(
                        p.getFechaRegistro().atStartOfDay(ZoneId.systemDefault()).toInstant()
                ));
            }

            dtos.add(dto);
        }

        return dtos;
    }

    public PerfilDTO getById(Integer id) {
        Perfil p = perfilRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Perfil no encontrado con id " + id));

        PerfilDTO dto = new PerfilDTO();
        dto.setId(p.getId());
        dto.setIdUsuario(p.getUsuario().getId());
        dto.setNombre(p.getNombre());
        dto.setApellidos(p.getApellidos());
        dto.setCurso(p.getCurso());
        dto.setFotoURL(p.getFotoURL());

        if (p.getFechaRegistro() != null) {
            dto.setFechaRegistro(Date.from(
                    p.getFechaRegistro().atStartOfDay(ZoneId.systemDefault()).toInstant()
            ));
        }

        return dto;
    }

    public PerfilDTO guardarPerfil(PerfilDTO dto) {

        Usuario usuario = usuarioRepository.findById(dto.getIdUsuario())
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado con id " + dto.getIdUsuario()));

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

        PerfilDTO dtoGuardado = new PerfilDTO();
        dtoGuardado.setId(guardado.getId());
        dtoGuardado.setIdUsuario(usuario.getId());
        dtoGuardado.setNombre(guardado.getNombre());
        dtoGuardado.setApellidos(guardado.getApellidos());
        dtoGuardado.setCurso(guardado.getCurso());
        dtoGuardado.setFotoURL(guardado.getFotoURL());

        dtoGuardado.setFechaRegistro(Date.from(
                guardado.getFechaRegistro().atStartOfDay(ZoneId.systemDefault()).toInstant()
        ));

        return dtoGuardado;
    }

    public void eliminar(Integer id) {
        perfilRepository.deleteById(id);
    }
}