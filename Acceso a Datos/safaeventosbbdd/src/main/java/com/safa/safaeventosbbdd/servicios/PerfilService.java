package com.safa.safaeventosbbdd.servicios;

import com.safa.safaeventosbbdd.dto.PerfilDTO;
import com.safa.safaeventosbbdd.dto.UsuarioDTO;
import com.safa.safaeventosbbdd.modelos.Perfil;
import com.safa.safaeventosbbdd.modelos.Usuario;
import com.safa.safaeventosbbdd.repositorios.PerfilRepository;
import com.safa.safaeventosbbdd.repositorios.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class PerfilService {

    @Autowired
    private PerfilRepository perfilRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    public List<PerfilDTO> getAll() {
        List<Perfil> perfiles = perfilRepository.findAll();
        List<PerfilDTO> dtos = new ArrayList<>();

        for (Perfil p : perfiles) {

            Usuario u = p.getIdUsuario();
            UsuarioDTO usuarioDTO = new UsuarioDTO(
                    u.getId(),
                    u.getEmail(),
                    u.getContrasenia(),
                    u.getRol(),
                    u.getVerificacion()
            );

            PerfilDTO dto = new PerfilDTO();
            dto.setId(p.getId());
            dto.setUsuarioDTO(usuarioDTO);
            dto.setNombre(p.getNombre());
            dto.setApellidos(p.getApellidos());
            dto.setCurso(p.getCurso());
            dto.setFechaRegistro(Date.from(p.getFechaRegistro().atStartOfDay(ZoneId.systemDefault()).toInstant()));

            dtos.add(dto);
        }

        return dtos;
    }

    public PerfilDTO getById(Integer id) {
        Perfil p = perfilRepository.findById(id).orElse(null);
        if (p == null) return null;

        Usuario u = p.getIdUsuario();
        UsuarioDTO usuarioDTO = new UsuarioDTO(
                u.getId(),
                u.getEmail(),
                u.getContrasenia(),
                u.getRol(),
                u.getVerificacion()
        );

        PerfilDTO dto = new PerfilDTO();
        dto.setId(p.getId());
        dto.setUsuarioDTO(usuarioDTO);
        dto.setNombre(p.getNombre());
        dto.setApellidos(p.getApellidos());
        dto.setCurso(p.getCurso());
        dto.setFechaRegistro(Date.from(p.getFechaRegistro().atStartOfDay(ZoneId.systemDefault()).toInstant()));

        return dto;
    }

    public PerfilDTO guardarPerfil(PerfilDTO dto) {

        Perfil perfil = new Perfil();

        Usuario u = usuarioRepository.findById(dto.getUsuarioDTO().getId())
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado con id: " + dto.getUsuarioDTO().getId()));

        perfil.setIdUsuario(u);
        perfil.setNombre(dto.getNombre());
        perfil.setApellidos(dto.getApellidos());
        perfil.setCurso(dto.getCurso());
        perfil.setFechaRegistro(dto.getFechaRegistro().toInstant().atZone(ZoneId.systemDefault()).toLocalDate());

        Perfil guardado = perfilRepository.save(perfil);

        UsuarioDTO usuarioDTO = new UsuarioDTO(
                u.getId(),
                u.getEmail(),
                u.getContrasenia(),
                u.getRol(),
                u.getVerificacion()
        );

        PerfilDTO dtoGuardado = new PerfilDTO();
        dtoGuardado.setId(guardado.getId());
        dtoGuardado.setUsuarioDTO(usuarioDTO);
        dtoGuardado.setNombre(guardado.getNombre());
        dtoGuardado.setApellidos(guardado.getApellidos());
        dtoGuardado.setCurso(guardado.getCurso());
        dtoGuardado.setFechaRegistro(Date.from(guardado.getFechaRegistro().atStartOfDay(ZoneId.systemDefault()).toInstant()));

        return dtoGuardado;
    }

    public void eliminar(Integer id) {
        perfilRepository.deleteById(id);
    }
}