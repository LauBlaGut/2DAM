package com.safa.safaeventosbbdd.servicios;

import com.safa.safaeventosbbdd.dto.EventoDTO;
import com.safa.safaeventosbbdd.dto.UsuarioActivoDTO;
import com.safa.safaeventosbbdd.dto.UsuarioDTO;
import com.safa.safaeventosbbdd.modelos.enums.CategoriaEventos;
import com.safa.safaeventosbbdd.modelos.enums.MetodoPago;
import com.safa.safaeventosbbdd.modelos.enums.RolUsuario;
import com.safa.safaeventosbbdd.repositorios.EventoRepository;
import com.safa.safaeventosbbdd.repositorios.InscripcionRepository;
import com.safa.safaeventosbbdd.repositorios.UsuarioRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class UsuarioServiceTest {

    @Autowired
    UsuarioService usuarioService;

    @Autowired
    private InscripcionService inscripcionService;

    @Autowired
    private EventoService eventoService;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private EventoRepository eventoRepository;

    @Autowired
    private InscripcionRepository inscripcionRepository;

    private Integer idOrganizador;
    private Integer idEvento1;
    private Integer idEvento2;

    @BeforeEach
    void cargarDatos(){
        UsuarioDTO usuarioDTO = new UsuarioDTO();
        usuarioDTO.setEmail("organizador_test@test.com");
        usuarioDTO.setContrasenia("1234");
        usuarioDTO.setRolUsuario(RolUsuario.ORGANIZADOR);
        usuarioDTO.setVerificacion(true);

        UsuarioDTO orgGuardado = usuarioService.guardarUsuario(usuarioDTO);
        this.idOrganizador = orgGuardado.getId();

        EventoDTO e1 = new EventoDTO();
        e1.setTitulo("Evento Global 1");
        e1.setDescripcion("Desc");
        e1.setFecha(LocalDate.of(2025, 12, 25));
        e1.setHora(LocalTime.of(18, 30));
        e1.setUbicacion("Sala 1");
        e1.setPrecio(10.0);
        e1.setCategoria(CategoriaEventos.CULTURA);
        e1.setFoto("f1.jpg");
        e1.setIdOrganizador(this.idOrganizador);

        EventoDTO e1Guardado = eventoService.guardarEvento(e1);
        this.idEvento1 = e1Guardado.getId();

        EventoDTO e2 = new EventoDTO();
        e2.setTitulo("Evento Global 2");
        e2.setDescripcion("Desc");
        e2.setFecha(LocalDate.of(2025, 1, 25));
        e2.setHora(LocalTime.of(20, 00));
        e2.setUbicacion("Sala 2");
        e2.setPrecio(20.0);
        e2.setCategoria(CategoriaEventos.CULTURA);
        e2.setFoto("f2.jpg");
        e2.setIdOrganizador(this.idOrganizador);

        EventoDTO e2Guardado = eventoService.guardarEvento(e2);
        this.idEvento2 = e2Guardado.getId();
    }

    @Test
    public void crearUsuarioTrue(){
        UsuarioDTO usuarioDTO = new UsuarioDTO();
        usuarioDTO.setVerificacion(true);
        usuarioDTO.setRolUsuario(RolUsuario.ALUMNO);
        usuarioDTO.setEmail("alumno@gmail.com");
        usuarioDTO.setContrasenia("123456");

        UsuarioDTO usuarioDTO1 = usuarioService.guardarUsuario(usuarioDTO);
        this.idOrganizador = usuarioDTO.getId();


        assertNotNull(usuarioDTO1);
    }



    @Test
    public void crearUsuarioNombreEnBlancoTestFalse(){
        UsuarioDTO usuarioDTO = new UsuarioDTO();
        usuarioDTO.setVerificacion(true);
        usuarioDTO.setRolUsuario(RolUsuario.ALUMNO);
        usuarioDTO.setEmail("");
        usuarioDTO.setContrasenia("123456");

        assertThrows(IllegalArgumentException.class, () -> {
            this.usuarioService.guardarUsuario(usuarioDTO);
        });

    }

    @Test
    public void usuarioConMasEventosTrue(){
        // GIVEN

        // WHEN
        UsuarioActivoDTO resultado = usuarioService.obtenerUsuarioMasActivo();

        // THEN
        assertNotNull(resultado, "El resultado no debería ser nulo.");
        assertEquals(idOrganizador, resultado.getId(), "El usuario con más eventos debería ser el organizador de prueba");
    }

    @Test
    public void usuarioConMasEventosFalse(){
        // GIVEN

        // WHEN
        UsuarioActivoDTO resultado = usuarioService.obtenerUsuarioMasActivo();

        // THEN
        assertNotNull(resultado, "El resultado no debería ser nulo.");
        assertNotEquals(9999, resultado.getId(), "El ID no debería coincidir con un usuario inexistente");
    }

    @Test
    public void eventosUsuarioParticipaTrue(){
        //GIVEN
        UsuarioDTO alumno = new UsuarioDTO();
        alumno.setEmail("alumno@alumno.com");
        alumno.setContrasenia("1234");
        alumno.setRolUsuario(RolUsuario.ALUMNO);
        alumno.setVerificacion(true);

        UsuarioDTO alumnoGuardado = usuarioService.guardarUsuario(alumno);

        inscripcionService.inscribirUsuarioAEvento(alumnoGuardado.getId(), this.idEvento1, MetodoPago.EFECTIVO);

        // WHEN
        List<EventoDTO> listaEventos = inscripcionService.getEventos_Usuario(alumnoGuardado.getId());

        // THEN
        assertNotNull(listaEventos, "La lista no debe ser nula.");
        assertFalse(listaEventos.isEmpty(), "La lista no debería estar vacía.");

    }

    @Test
    public void eventosUsuarioParticipaFalse(){
        //GIVEN
        UsuarioDTO alumno = new UsuarioDTO();
        alumno.setEmail("alumno@alumno.com");
        alumno.setContrasenia("1234");
        alumno.setRolUsuario(RolUsuario.ALUMNO);
        alumno.setVerificacion(true);

        UsuarioDTO alumnoGuardado = usuarioService.guardarUsuario(alumno);

        // WHEN
        List<EventoDTO> listaEventos = inscripcionService.getEventos_Usuario(alumnoGuardado.getId());

        // THEN
        assertNotNull(listaEventos, "La lista nunca debería ser null, sino vacía");
        assertTrue(listaEventos.isEmpty(), "La lista debería estar vacía porque no hay inscripciones.");
    }




}
