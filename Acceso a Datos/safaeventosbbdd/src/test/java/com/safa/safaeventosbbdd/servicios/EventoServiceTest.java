package com.safa.safaeventosbbdd.servicios;

import com.safa.safaeventosbbdd.dto.*;
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
    private InscripcionService inscripcionService;

    @Autowired
    private PerfilService perfilService;

    @Autowired
    private FotoEventoService FotoEventoService;


    private Integer idEventoPrueba;
    private Integer idOrganizadorPrueba;


    @BeforeEach
    void cargarDatos() {
        UsuarioDTO usuarioDTO = new UsuarioDTO();
        usuarioDTO.setEmail("usuario@usuario.com");
        usuarioDTO.setContrasenia("contrasenia123");
        usuarioDTO.setRolUsuario(RolUsuario.ORGANIZADOR);
        usuarioDTO = usuarioService.guardarUsuario(usuarioDTO);
        this.idOrganizadorPrueba = usuarioDTO.getId();

        UsuarioDTO alumnoDTO = new UsuarioDTO();
        alumnoDTO.setEmail("alumno_relleno@test.com");
        alumnoDTO.setContrasenia("1234");
        alumnoDTO.setRolUsuario(RolUsuario.ALUMNO);
        alumnoDTO = usuarioService.guardarUsuario(alumnoDTO);
        Integer idAlumno = alumnoDTO.getId();

        PerfilDTO perfilDTO = new PerfilDTO();
        perfilDTO.setNombre("Manuel");
        perfilDTO.setApellidos("García García");
        perfilDTO.setCurso(Curso.BACH_DOS);
        perfilDTO.setIdUsuario(usuarioDTO.getId());

        // perfilService.guardarPerfil(perfilDTO);

        EventoDTO a = new EventoDTO();
        a.setTitulo("Evento Prueba 1");
        a.setDescripcion("Descripcion Prueba");
        a.setFecha(LocalDate.of(2024, 12, 25));
        a.setHora(LocalTime.of(18, 30));
        a.setUbicacion("Ubicación Prueba");
        a.setPrecio(15.0);
        a.setCategoria(CategoriaEventos.CULTURA);
        a.setFoto("foto_prueba.jpg");
        a.setIdOrganizador(usuarioDTO.getId());
        a = eventoService.guardarEvento(a);
        this.idEventoPrueba = a.getId();

        inscripcionService.inscribirUsuarioAEvento(idAlumno, a.getId(), MetodoPago.EFECTIVO);


        EventoDTO a2 = new EventoDTO();
        a2.setTitulo("Evento Prueba 2");
        a2.setDescripcion("Descripcion Prueba2");
        a2.setFecha(LocalDate.of(2024, 1, 25));
        a2.setHora(LocalTime.of(20, 00));
        a2.setUbicacion("Ubicación Prueba2");
        a2.setPrecio(25.0);
        a2.setCategoria(CategoriaEventos.CULTURA);
        a2.setFoto("foto_prueba2.jpg");
        a2.setIdOrganizador(usuarioDTO.getId());
        a2 = eventoService.guardarEvento(a2);
        this.idEventoPrueba = a2.getId();
        inscripcionService.inscribirUsuarioAEvento(idAlumno, a2.getId(), MetodoPago.EFECTIVO);


        EventoDTO a3 = new EventoDTO();
        a3.setTitulo("Evento Relleno 3");
        a3.setDescripcion("Relleno");
        a3.setFecha(LocalDate.now());
        a3.setHora(LocalTime.of(10, 0));
        a3.setUbicacion("Aula 1");
        a3.setPrecio(5.0);
        a3.setCategoria(CategoriaEventos.OTROS);
        a3.setFoto("foto3.jpg");
        a3.setIdOrganizador(usuarioDTO.getId());
        a3 = eventoService.guardarEvento(a3);
        inscripcionService.inscribirUsuarioAEvento(idAlumno, a3.getId(), MetodoPago.EFECTIVO);

        EventoDTO a4 = new EventoDTO();
        a4.setTitulo("Evento Relleno 4");
        a4.setDescripcion("Relleno");
        a4.setFecha(LocalDate.now());
        a4.setHora(LocalTime.of(10, 0));
        a4.setUbicacion("Aula 2");
        a4.setPrecio(5.0);
        a4.setCategoria(CategoriaEventos.DEPORTES);
        a4.setFoto("foto4.jpg");
        a4.setIdOrganizador(usuarioDTO.getId());
        a4 = eventoService.guardarEvento(a4);
        inscripcionService.inscribirUsuarioAEvento(idAlumno, a4.getId(), MetodoPago.EFECTIVO);


        EventoDTO a5 = new EventoDTO();
        a5.setTitulo("Evento Relleno 5");
        a5.setDescripcion("Relleno");
        a5.setFecha(LocalDate.now());
        a5.setHora(LocalTime.of(10, 0));
        a5.setUbicacion("Aula 3");
        a5.setPrecio(5.0);
        a5.setCategoria(CategoriaEventos.ACADEMICO);
        a5.setFoto("foto5.jpg");
        a5.setIdOrganizador(usuarioDTO.getId());
        a5 = eventoService.guardarEvento(a5);
        inscripcionService.inscribirUsuarioAEvento(idAlumno, a5.getId(), MetodoPago.EFECTIVO);


        EventoDTO a6 = new EventoDTO();
        a6.setTitulo("Evento Relleno 6");
        a6.setDescripcion("Este debería quedarse fuera del Top 5 si ordenamos por algo más o ser el 6º");
        a6.setFecha(LocalDate.now());
        a6.setHora(LocalTime.of(10, 0));
        a6.setUbicacion("Aula 4");
        a6.setPrecio(5.0);
        a6.setCategoria(CategoriaEventos.CULTURA);
        a6.setFoto("foto6.jpg");
        a6.setIdOrganizador(usuarioDTO.getId());
        a6 = eventoService.guardarEvento(a6);
        inscripcionService.inscribirUsuarioAEvento(idAlumno, a6.getId(), MetodoPago.EFECTIVO);
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
    public void crearEventoTrue(){
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
    public void crearEventoNombreEnBlancoFalse(){
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
    public void filtrarPorFechaTestTrue(){
        eventoService.getAll();
        List<EventoDTO> lista = eventoService.filtrarEventos("25/12/2024", CategoriaEventos.CULTURA);
        assertEquals(1, lista.size());
    }

    @Test
    public void filtrarPorFechaInvalidaTestFalse(){

        assertThrows(
                RuntimeException.class,
                () -> eventoService.filtrarEventos("Laura",CategoriaEventos.ACADEMICO)
        );
    }

    @Test
    public void obtenerDetallesEventoTrue() {
        // GIVEN
        Integer idABuscar = this.idEventoPrueba;

        // THEN
        EventoDTO resultado = this.eventoService.getById(idABuscar);

        // WHEN
        assertNotNull(resultado, "El detalle del evento no debería ser nulo");
        assertEquals("Evento Prueba 2", resultado.getTitulo(), "El título debería coincidir con el del segundo evento cargado");
        assertEquals("Ubicación Prueba2", resultado.getUbicacion(), "La ubicación debe ser la correcta");
        assertNotNull(resultado.getHora(), "La hora debe haberse mapeado correctamente");
    }

    @Test
    public void obtenerDetallesEventoNotFoundFalse() {
        // GIVEN
        Integer idInexistente = 999;

        // THEN + WHEN
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
        // GIVEN
        Integer idEventoInexistente = 9999;
        Integer idUsuario = this.idOrganizadorPrueba;

        // THEN + WHEN
        assertThrows(ElementoNoEncontradoException.class, () -> {
            inscripcionService.inscribirUsuarioAEvento(idUsuario, idEventoInexistente, MetodoPago.PAYPAL);
        });
    }

    @Test
    public void subirFotoEventoTrue() {
        // GIVEN
        FotoEventoDTO fotoDTO = new FotoEventoDTO();
        fotoDTO.setRutaFoto("ruta/a/la/foto.jpg");
        fotoDTO.setEventoDTO(new EventoDTO());
        fotoDTO.setUsuarioDTO(new UsuarioDTO());
        fotoDTO.setFechaSubida(LocalDateTime.now());
        fotoDTO.setId(1);

        Integer idUsuario = this.idOrganizadorPrueba;
        Integer idEvento = this.idEventoPrueba;

        // WHEN
        FotoEventoDTO resultado = FotoEventoService.guardarFoto(idEvento, idUsuario, fotoDTO);

        // THEN
        assertNotNull(resultado);
        assertNotNull(resultado.getId(), "La foto debería tener un ID generado por la BBDD.");
        assertEquals("ruta/a/la/foto.jpg", resultado.getRutaFoto());
        assertEquals(idUsuario, resultado.getUsuarioDTO().getId());
        assertEquals(idEvento, resultado.getEventoDTO().getId());
    }

    @Test
    public void subirFotoEventoFalse() {
        // GIVEN
        FotoEventoDTO fotoDTO = new FotoEventoDTO();
        fotoDTO.setRutaFoto("ruta/a/la/foto.jpg");
        fotoDTO.setEventoDTO(new EventoDTO());
        fotoDTO.setUsuarioDTO(new UsuarioDTO());
        fotoDTO.setFechaSubida(LocalDateTime.now());
        fotoDTO.setId(1);

        Integer idUsuarioInexistente = 9999;
        Integer idEvento = this.idEventoPrueba;

        // THEN + WHEN
        assertThrows(ElementoNoEncontradoException.class, () -> {
            FotoEventoService.guardarFoto(idEvento, idUsuarioInexistente, fotoDTO);
        });
    }

    @Test
    public void top5EventosTrue() {
        // GIVEN

        // WHEN
        List<EventoTopDTO> listaTop5 = inscripcionService.obtenerTop5Eventos();

        // THEN
        assertNotNull(listaTop5, "La lista no debería ser nula.");
        assertTrue(listaTop5.size() <= 5, "La lista debería contener como máximo 5 eventos.");
    }

    @Test
    public void top5EventosFalse() {
        // GIVEN

        // WHEN
        List<EventoTopDTO> listaTop5 = inscripcionService.obtenerTop5Eventos();

        // THEN
        assertNotNull(listaTop5, "La lista no debería ser nula.");
        assertFalse(listaTop5.size() > 5, "La lista no debería contener más de 5 eventos.");
    }



}
