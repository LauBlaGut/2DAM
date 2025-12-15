package com.safa.safaeventosbbdd.servicios;

import com.safa.safaeventosbbdd.dto.UsuarioActivoDTO;
import com.safa.safaeventosbbdd.dto.UsuarioDTO;
import com.safa.safaeventosbbdd.exception.ElementoNoEncontradoException;
import com.safa.safaeventosbbdd.exception.EliminarNoExistenteException;
import com.safa.safaeventosbbdd.modelos.Usuario;
import com.safa.safaeventosbbdd.repositorios.UsuarioRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class UsuarioService implements UserDetailsService {


    private UsuarioRepository usuarioRepository;

    // Cargar usuario por email (login)
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return usuarioRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado: " + email));
    }

    // Obtener todos los usuarios (DTO)
    public List<UsuarioDTO> getAll() {

        List<Usuario> usuarios = usuarioRepository.findAll();
        List<UsuarioDTO> dtos = new ArrayList<>();

        for (Usuario u : usuarios) {
            UsuarioDTO dto = new UsuarioDTO();
            dto.setId(u.getId());
            dto.setEmail(u.getEmail());
            dto.setContrasenia(u.getContrasenia());
            dto.setRolUsuario(u.getRol());
            dto.setVerificacion(u.getVerificacion());
            dtos.add(dto);
        }

        return dtos;
    }

    // Obtener usuario por id
    public UsuarioDTO getById(Integer id) {

        Usuario u = usuarioRepository.findById(id)
                .orElseThrow(() ->
                        new ElementoNoEncontradoException(
                                "No se encontró el usuario con ID " + id
                        ));

        UsuarioDTO dto = new UsuarioDTO();
        dto.setId(u.getId());
        dto.setEmail(u.getEmail());
        dto.setContrasenia(u.getContrasenia());
        dto.setRolUsuario(u.getRol());
        dto.setVerificacion(u.getVerificacion());

        return dto;
    }

    // Guardar usuario desde DTO
    public UsuarioDTO guardarUsuario(UsuarioDTO dto) {

        Usuario u = new Usuario();
        u.setEmail(dto.getEmail());
        u.setContrasenia(dto.getContrasenia());
        u.setRol(dto.getRolUsuario());
        u.setVerificacion(dto.getVerificacion());

        Usuario guardado = usuarioRepository.save(u);

        UsuarioDTO respuesta = new UsuarioDTO();
        respuesta.setId(guardado.getId());
        respuesta.setEmail(guardado.getEmail());
        respuesta.setContrasenia(guardado.getContrasenia());
        respuesta.setRolUsuario(guardado.getRol());
        respuesta.setVerificacion(guardado.getVerificacion());

        return respuesta;
    }


    // Eliminar usuario
    public void eliminar(Integer id) {

        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() ->
                        new EliminarNoExistenteException(
                                "No se puede eliminar el usuario con ID " + id + " porque no existe"
                        ));

        usuarioRepository.delete(usuario);
    }
    // Obtener el usuario más activo
    public UsuarioActivoDTO obtenerUsuarioMasActivo() {
        UsuarioActivoDTO dto = usuarioRepository.usuarioMasActivo();

        if (dto == null) {
            throw new ElementoNoEncontradoException("No hay usuarios con actividad registrada.");
        }

        return dto;
    }
}