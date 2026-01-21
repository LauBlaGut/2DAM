package com.safa.safaeventosbbdd.servicios;

import com.safa.safaeventosbbdd.dto.UsuarioDTO;
import com.safa.safaeventosbbdd.modelos.Usuario;
import com.safa.safaeventosbbdd.modelos.enums.RolUsuario;
import com.safa.safaeventosbbdd.repositorios.UsuarioRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verifyNoInteractions;

@ExtendWith(MockitoExtension.class)
public class UsuarioServiceIntegrationTest {

    @InjectMocks
    private UsuarioService usuarioService;

    @Mock
    private UsuarioRepository usuarioRepository;

    @Test
    @DisplayName("Crear usuario sin algún dato necesario lanza excepción.")
    void testGuardarUsuarioDatosIncompletos() {
        // Given
        // El email está vacío
        UsuarioDTO dto = new UsuarioDTO();
        dto.setContrasenia("123456");
        dto.setRolUsuario(RolUsuario.ALUMNO);
        dto.setVerificacion(true);

        //When + then
        Exception exception = assertThrows(RuntimeException.class, () -> {usuarioService.guardarUsuario(dto);});
        assertEquals("El email no puede estar vacío", exception.getMessage());

        //Verificamos que se llamó al repositorio
        verifyNoInteractions(usuarioRepository);

    }
}
