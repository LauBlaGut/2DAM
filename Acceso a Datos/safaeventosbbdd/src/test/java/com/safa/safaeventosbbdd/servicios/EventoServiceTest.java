package com.safa.safaeventosbbdd.servicios;

import com.safa.safaeventosbbdd.dto.EventoDTO;
import com.safa.safaeventosbbdd.dto.InscripcionDTO;
import com.safa.safaeventosbbdd.dto.PerfilDTO;
import com.safa.safaeventosbbdd.dto.UsuarioDTO;
import com.safa.safaeventosbbdd.exception.ElementoNoEncontradoException;
import com.safa.safaeventosbbdd.modelos.Usuario;
import com.safa.safaeventosbbdd.modelos.enums.CategoriaEventos;
import com.safa.safaeventosbbdd.modelos.enums.Curso;
import com.safa.safaeventosbbdd.modelos.enums.MetodoPago;
import com.safa.safaeventosbbdd.modelos.enums.RolUsuario;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
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
    private InscripcionService inscripcionService;

    @Autowired
    private PerfilService perfilService;


    private Integer idEventoPrueba;
    private Integer idOrganizadorPrueba;

    //Antes de ejecutar los test se tiene que cargar la bbdd


    @BeforeEach
    void cargarDatos(){
        UsuarioDTO usuarioDTO = new UsuarioDTO();
        usuarioDTO.setEmail("usuario@usuario.com");
        usuarioDTO.setContrasenia("contrasenia123");
        usuarioDTO.setRolUsuario(RolUsuario.ORGANIZADOR);

        usuarioDTO = usuarioService.guardarUsuario(usuarioDTO);

        this.idOrganizadorPrueba = usuarioDTO.getId();

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

        a = eventoService.guardarEvento(a);
        this.idEventoPrueba = a.getId();

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

        a2 = eventoService.guardarEvento(a2);
        this.idEventoPrueba = a2.getId();
    }

    @Test
    public void buscarPorIdTest(){
        //Given
        //PREVIOS
        Usuario u = new Usuario();
        u.setEmail ("usuarioPrueba@mail.com");

        //When
        //EJECUCIÓN DE PRUEBA DEL METODO
        EventoDTO dto = eventoService.getById(idEventoPrueba);

        //Then
        //COMPROBACIONES
        assertNotNull(dto, "El evento no se ha encontrado en la base de datos");
    }

    @Test
    public void createEventTestTrue(){
        EventoDTO dto = new EventoDTO();
        dto.setTitulo("Evento Test");
        dto.setDescripcion("Descripción Test");
        dto.setFecha(LocalDate.of(2024, 01, 25));
        dto.setHora(LocalTime.of(19,00));
        dto.setUbicacion("Ubicación Test");
        dto.setPrecio(10.0);
        dto.setCategoria(CategoriaEventos.CULTURA);
        dto.setFoto("foto_test.jpg");
        dto.setIdOrganizador(1);


        EventoDTO eventoCreado = eventoService.guardarEvento(dto);

        assertNotNull(eventoCreado, "El evento no se ha creado correctamente");
    }

    @Test
    public void createEventTestNameBlankFalse(){
        //Given
        Integer idExistente = usuarioService.getAll().get(0).getId();

        EventoDTO dto = new EventoDTO();
        dto.setTitulo("");
        dto.setDescripcion("Descripción Test");
        dto.setFecha(LocalDate.of(2024, 01, 25));
        dto.setHora(LocalTime.of(19,00));
        dto.setUbicacion("Ubicación Test");
        dto.setPrecio(10.0);
        dto.setCategoria(CategoriaEventos.CULTURA);
        dto.setFoto("foto_test.jpg");
        dto.setIdOrganizador(idExistente);

        // WHEN + THEN
        IllegalArgumentException exception = Assertions.assertThrows(IllegalArgumentException.class, () -> {
            eventoService.guardarEvento(dto);
        });

        System.out.println("MENSAJE RECIBIDO: " + exception.getMessage());
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

    @Test
    public void getDetallesEventoTrue() {
        // GIVEN
        Integer idABuscar = this.idEventoPrueba;

        // THEN
        EventoDTO resultado = this.eventoService.getById(idABuscar);

        // WHEN
        assertNotNull(resultado, "El detalle del evento no debería ser nulo");
        assertEquals("Evento Prueba2", resultado.getTitulo(), "El título debería coincidir con el del segundo evento cargado");
        assertEquals("Ubicación Prueba2", resultado.getUbicacion(), "La ubicación debe ser la correcta");
        assertNotNull(resultado.getHora(), "La hora debe haberse mapeado correctamente");
    }

    @Test
    public void getDetallesEventoNotFoundFalse() {
        // GIVEN
        Integer idInexistente = 999;

        // THEN + WHEN
        // Aquí usamos la excepción que use tu servicio (ej. ElementoNoEncontradoException o RuntimeException)
        assertThrows(RuntimeException.class, () -> {
            this.eventoService.getById(idInexistente);
        }, "Debería lanzar una excepción al no encontrar el evento");
    }

    @Test
    public void modificarEventoTrue() {
        // GIVEN
        EventoDTO cambios = new EventoDTO();
        cambios.setId(idEventoPrueba); // El ID es obligatorio para saber qué editar
        cambios.setTitulo("Título Actualizado en BBDD");
        cambios.setUbicacion("Nueva Ubicación Real");
        cambios.setDescripcion("Descripción cambiada");
        cambios.setPrecio(25.0);
        cambios.setFecha(LocalDate.of(2026, 10, 10));
        cambios.setHora(LocalTime.of(18, 0));
        cambios.setIdOrganizador(idOrganizadorPrueba);

        // WHEN
        eventoService.editarEvento(cambios);

        // THEN
        EventoDTO eventoEnBBDD = eventoService.getById(idEventoPrueba);

        assertNotNull(eventoEnBBDD);
        assertEquals("Título Actualizado en BBDD", eventoEnBBDD.getTitulo());
        assertEquals("Nueva Ubicación Real", eventoEnBBDD.getUbicacion());
        assertEquals(25.0, eventoEnBBDD.getPrecio());
    }

    @Test
    public void modificarEventoFalse() {
        // GIVEN
        EventoDTO dtoInexistente = new EventoDTO();
        dtoInexistente.setId(9999);
        dtoInexistente.setTitulo("No importa");

        // THEN + WHEN
        assertThrows(ElementoNoEncontradoException.class, () -> {
            eventoService.editarEvento(dtoInexistente);
        });
    }

    @Test
    public void inscribirAsistenteTrue() {
        // GIVEN
        Integer idEvento = this.idEventoPrueba;
        Integer idUsuario = this.idOrganizadorPrueba;

        // WHEN
        InscripcionDTO resultado = inscripcionService.inscribirUsuarioAEvento(idUsuario, idEvento, MetodoPago.EFECTIVO);

        // THEN
        assertNotNull(resultado);
        assertNotNull(resultado.getId(), "La inscripción debería tener un ID generado por la BBDD");
        assertEquals(idEvento, resultado.getEventoDTO().getId());
        assertEquals(idUsuario, resultado.getUsuarioDTO().getId());
    }

    @Test
    public void inscribirAsistenteFalse() {
        // GIVEN: Un ID de evento que no existe
        Integer idEventoInexistente = 9999;
        Integer idUsuario = this.idOrganizadorPrueba;

        // THEN + WHEN: Debe fallar porque el evento no existe
        assertThrows(ElementoNoEncontradoException.class, () -> {
            inscripcionService.inscribirUsuarioAEvento(idUsuario, idEventoInexistente, MetodoPago.PAYPAL);
        });
    }



}
