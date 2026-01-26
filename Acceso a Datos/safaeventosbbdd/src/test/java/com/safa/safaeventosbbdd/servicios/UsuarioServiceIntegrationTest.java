package com.safa.safaeventosbbdd.servicios;

import com.safa.safaeventosbbdd.dto.EventoDTO;
import com.safa.safaeventosbbdd.dto.UsuarioActivoDTO;
import com.safa.safaeventosbbdd.dto.UsuarioDTO;
import com.safa.safaeventosbbdd.modelos.Evento;
import com.safa.safaeventosbbdd.modelos.Inscripcion;
import com.safa.safaeventosbbdd.modelos.Usuario;
import com.safa.safaeventosbbdd.modelos.enums.CategoriaEventos;
import com.safa.safaeventosbbdd.modelos.enums.RolUsuario;
import com.safa.safaeventosbbdd.repositorios.InscripcionRepository;
import com.safa.safaeventosbbdd.repositorios.UsuarioRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verifyNoInteractions;

@SpringBootTest
public class UsuarioServiceIntegrationTest {

    @InjectMocks
    private UsuarioService usuarioService;

    @Mock
    private UsuarioRepository usuarioRepository;

    @InjectMocks
    private InscripcionService inscripcionService;

    @Mock
    private InscripcionRepository inscripcionRepository;


    @Test
    @DisplayName("Crear usuario sin algún dato necesario lanza excepción.")
    void testGuardarUsuarioDatosIncompletos() {
        // Given
        // El email está vacío
        UsuarioDTO dto = new UsuarioDTO();
        dto.setEmail("");
        dto.setContrasenia("123456");
        dto.setRolUsuario(RolUsuario.ALUMNO);
        dto.setVerificacion(true);

        // THEN
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            this.usuarioService.guardarUsuario(dto);
        });

        assertEquals("El email no puede estar vacío", exception.getMessage());

        // WHEN
        Mockito.verifyNoInteractions(this.usuarioRepository);
    }

    @Test
    @DisplayName("Usuario con más eventos publicado y participado.")
    public void usuarioConMasEventosIntegrationTest() {
        // GIVEN
        UsuarioActivoDTO usuario = new UsuarioActivoDTO(
                1,
                "hola@gmail.com",
                "Hola",
                "Holaapellido",
                8L,
                5L,
                1000L
        );

        Mockito.when(usuarioRepository.usuarioMasActivo()).thenReturn(usuario);


        // WHEN
        UsuarioActivoDTO resultado = usuarioService.obtenerUsuarioMasActivo();

        // THEN
        assertNotNull(resultado, "El resultado no debería ser nulo.");
        assertEquals(1, resultado.getId(), "El usuario con más eventos debería ser el organizador de prueba");
    }

    @Test
    @DisplayName("Eventos en los que el usuario participa.")
    public void eventosUsuarioParticipaIntegrationTest() {
        // GIVEN
        Integer idUsuario = 1;

        Usuario organizador = new Usuario();
        organizador.setId(1);
        organizador.setEmail("test@test.com");
        organizador.setRol(RolUsuario.ORGANIZADOR);
        organizador.setVerificacion(true);
        organizador.setContrasenia("123456");

        Usuario participante = new Usuario();
        participante.setId(idUsuario);
        participante.setEmail("test2@test.com");
        participante.setRol(RolUsuario.ALUMNO);
        participante.setVerificacion(true);
        participante.setContrasenia("123456");

        Evento evento1 = new Evento();
        evento1.setId(10);
        evento1.setTitulo("Evento Global 1");
        evento1.setDescripcion("Desc");
        evento1.setFechaHora(LocalDateTime.of(2025, 12, 25, 18, 30));
        evento1.setUbicacion("Sala 1");
        evento1.setPrecio(10.0);
        evento1.setCategoria(CategoriaEventos.CULTURA);
        evento1.setUsuario(organizador);

        Evento evento2 = new Evento();
        evento2.setId(20);
        evento2.setTitulo("Evento Global 2");
        evento2.setDescripcion("Desc");
        evento2.setFechaHora(LocalDateTime.of(2025, 12, 25, 18, 30));
        evento2.setUbicacion("Sala 1");
        evento2.setPrecio(10.0);
        evento2.setCategoria(CategoriaEventos.CULTURA);
        evento2.setUsuario(organizador);


        Inscripcion inscripcion1 = new Inscripcion();
        inscripcion1.setId(50);
        inscripcion1.setUsuario(participante);
        inscripcion1.setEvento(evento1);

        Inscripcion inscripcion2 = new Inscripcion();
        inscripcion2.setId(51);
        inscripcion2.setUsuario(participante);
        inscripcion2.setEvento(evento2);

        List<Inscripcion> listaInscripciones = List.of(inscripcion1, inscripcion2);

        Mockito.when(inscripcionRepository.findByUsuario_Id(Mockito.anyInt())).thenReturn(listaInscripciones);

        // WHEN
        List<EventoDTO> resultado = inscripcionService.getEventos_Usuario(idUsuario);

        // THEN
        assertNotNull(resultado, "La lista no debería ser nula");
        assertEquals(2, resultado.size(), "Debería haber 2 eventos inscritos");
        assertEquals("Evento Global 1", resultado.get(0).getTitulo(), "El título del primer evento debe coincidir");
        assertEquals("Evento Global 2", resultado.get(1).getTitulo(), "El título del segundo evento debe coincidir");

        Mockito.verify(inscripcionRepository).findByUsuario_Id(Mockito.anyInt());
    }

}
