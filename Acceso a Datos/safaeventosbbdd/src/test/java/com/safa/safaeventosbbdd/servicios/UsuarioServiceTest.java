package com.safa.safaeventosbbdd.servicios;

import com.safa.safaeventosbbdd.dto.UsuarioDTO;
import com.safa.safaeventosbbdd.modelos.enums.RolUsuario;
import jakarta.validation.ConstraintViolationException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class UsuarioServiceTest {

    @Autowired
    UsuarioService usuarioService;

    @Test
    public void createUserTestTrue(){
        UsuarioDTO usuarioDTO = new UsuarioDTO();
        usuarioDTO.setVerificacion(true);
        usuarioDTO.setRolUsuario(RolUsuario.ALUMNO);
        usuarioDTO.setEmail("alumno@gmail.com");
        usuarioDTO.setContrasenia("123456");

        UsuarioDTO usuarioDTO1 = usuarioService.guardarUsuario(usuarioDTO);

        assertNotNull(usuarioDTO1);
    }

    @Test
    public void createUserTestEmailBlankFalse(){
        UsuarioDTO usuarioDTO = new UsuarioDTO();
        usuarioDTO.setVerificacion(true);
        usuarioDTO.setRolUsuario(RolUsuario.ALUMNO);
        usuarioDTO.setContrasenia("123456");

        assertThrows(ConstraintViolationException.class, ()->{
            usuarioService.guardarUsuario(usuarioDTO);
        });

    }

}
