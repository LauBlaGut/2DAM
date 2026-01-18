package com.safa.safaeventosbbdd.servicios;

import com.safa.safaeventosbbdd.dto.EventoDTO;
import com.safa.safaeventosbbdd.dto.PerfilDTO;
import com.safa.safaeventosbbdd.dto.UsuarioDTO;
import com.safa.safaeventosbbdd.modelos.Evento;
import com.safa.safaeventosbbdd.modelos.Usuario;
import com.safa.safaeventosbbdd.modelos.enums.CategoriaEventos;
import com.safa.safaeventosbbdd.modelos.enums.Curso;
import com.safa.safaeventosbbdd.modelos.enums.RolUsuario;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class EventoServiceTest {

    @Autowired
    private EventoService eventoService;

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private PerfilService perfilService;

    //Antes de ejecutar los test se tiene que cargar la bbdd


    @BeforeEach
    void cargarDatos(){
        UsuarioDTO usuarioDTO = new UsuarioDTO();
        usuarioDTO.setEmail("usuario@usuario.com");
        usuarioDTO.setContrasenia("contrasenia123");
        usuarioDTO.setRolUsuario(RolUsuario.ORGANIZADOR);

        usuarioDTO = usuarioService.guardarUsuario(usuarioDTO);

        PerfilDTO perfilDTO = new PerfilDTO();
        perfilDTO.setNombre("Manuel");
        perfilDTO.setApellidos("García García");
        perfilDTO.setCurso(Curso.BACH_DOS);
        perfilDTO.setIdUsuario(usuarioDTO.getId());


        EventoDTO a = new EventoDTO();
        a.setTitulo("Evento Prueba");
        a.setDescripcion("Descripcion Prueba");
        a.setFecha(LocalDate.of(2024, 12, 25));
        a.setHora(LocalTime.of(18,30));
        a.setUbicacion("Ubicación Prueba");
        a.setPrecio(15.0);
        a.setCategoria(CategoriaEventos.CULTURA);
        a.setFoto("foto_prueba.jpg");
        a.setIdOrganizador(usuarioDTO.getId());

        eventoService.guardarEvento(a);

        EventoDTO a2 = new EventoDTO();
        a2.setTitulo("Evento Prueba2");
        a2.setDescripcion("Descripcion Prueba2");
        a2.setFecha(LocalDate.of(2024, 01, 25));
        a2.setHora(LocalTime.of(20,00));
        a2.setUbicacion("Ubicación Prueba2");
        a2.setPrecio(25.0);
        a2.setCategoria(CategoriaEventos.CULTURA);
        a2.setFoto("foto_prueba2.jpg");
        a2.setIdOrganizador(usuarioDTO.getId());

        eventoService.guardarEvento(a2);
    }

    @Test
    public void buscarPorIdTest(){
        //Given
        //PREVIOS
        Usuario u = new Usuario();
        u.setEmail ("usuarioPrueba@mail.com");

        //When
        //EJECUCIÓN DE PRUEBA DEL MÉTODO
        EventoDTO dto = eventoService.getById(1);

        //Then
        //COMPROBACIONES
        assertNotNull(dto, "El evento no se ha encontrado en la base de datos");
    }

    @Test
    public void filterByDateTestTrue(){
        eventoService.getAll();
        List<EventoDTO> lista = eventoService.filtrarEventos("25/12/2024", CategoriaEventos.CULTURA);
        assertEquals(1, lista.size());
    }

    @Test
    public void filterByDateTestFakeDate(){

        assertThrows(
                RuntimeException.class,
                () -> eventoService.filtrarEventos("Laura",CategoriaEventos.ACADEMICO)
        );
    }

}
