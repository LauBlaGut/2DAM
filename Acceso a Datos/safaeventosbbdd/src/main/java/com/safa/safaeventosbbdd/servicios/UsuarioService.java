package com.safa.safaeventosbbdd.servicios;

import com.safa.safaeventosbbdd.dto.UsuarioActivoDTO;
import com.safa.safaeventosbbdd.dto.UsuarioDTO;
import com.safa.safaeventosbbdd.modelos.Usuario;
import com.safa.safaeventosbbdd.repositorios.UsuarioRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

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
        return usuarioRepository.findAll()
                .stream()
                .map(u -> new UsuarioDTO(
                        u.getId(),
                        u.getEmail(),
                        u.getContrasenia(),
                        u.getRol(),
                        u.getVerificacion()
                ))
                .toList();
    }

    // Obtener usuario por id
    public UsuarioDTO getById(Integer id) {
        Usuario u = usuarioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado con id: " + id));

        return new UsuarioDTO(
                u.getId(),
                u.getEmail(),
                u.getContrasenia(),
                u.getRol(),
                u.getVerificacion()
        );
    }

    // Guardar usuario desde DTO
    public UsuarioDTO guardarUsuario(UsuarioDTO dto) {
        Usuario u = new Usuario();
        u.setEmail(dto.getEmail());
        u.setContrasenia(dto.getContrasenia());
        u.setRol(dto.getRolUsuario());
        u.setVerificacion(dto.getVerificacion());

        Usuario guardado = usuarioRepository.save(u);

        return new UsuarioDTO(
                guardado.getId(),
                guardado.getEmail(),
                guardado.getContrasenia(),
                guardado.getRol(),
                guardado.getVerificacion()
        );
    }

    // Eliminar usuario
    public void eliminar(Integer id) {
        usuarioRepository.deleteById(id);
    }

    // Obtener el usuario más activo
    public UsuarioActivoDTO obtenerUsuarioMasActivo() {
        return usuarioRepository.usuarioMasActivo();
    }
}