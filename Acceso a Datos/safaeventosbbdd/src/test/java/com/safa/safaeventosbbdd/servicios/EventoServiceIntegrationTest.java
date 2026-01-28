package com.safa.safaeventosbbdd.servicios;


import com.safa.safaeventosbbdd.dto.*;
import com.safa.safaeventosbbdd.modelos.Evento;
import com.safa.safaeventosbbdd.modelos.FotoEvento;
import com.safa.safaeventosbbdd.modelos.Inscripcion;
import com.safa.safaeventosbbdd.modelos.Usuario;
import com.safa.safaeventosbbdd.modelos.enums.CategoriaEventos;
import com.safa.safaeventosbbdd.modelos.enums.MetodoPago;
import com.safa.safaeventosbbdd.modelos.enums.RolUsuario;
import com.safa.safaeventosbbdd.repositorios.EventoRepository;
import com.safa.safaeventosbbdd.repositorios.FotoEventoRepository;
import com.safa.safaeventosbbdd.repositorios.InscripcionRepository;
import com.safa.safaeventosbbdd.repositorios.UsuarioRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
public class EventoServiceIntegrationTest {

    @InjectMocks
    private EventoService eventoService;

    @Mock
    private EventoRepository eventoRepository;

    @Mock
    private UsuarioRepository usuarioRepository;

    @Mock
    private InscripcionRepository inscripcionRepository;

    @InjectMocks
    private InscripcionService inscripcionService;

    @InjectMocks
    private FotoEventoService fotoEventoService;

    @Mock
    private FotoEventoRepository fotoEventoRepository;


    private Evento evento;
    private Usuario organizador;
    
    @BeforeEach
    void cargarDatos() {
        organizador = new Usuario();
        organizador.setId(1);
        organizador.setEmail("organizador@test.com");

        evento = new Evento();
        evento.setId(1);
        evento.setTitulo("Concierto Solidario");
        evento.setDescripcion("Evento para recaudar fondos");
        evento.setFechaHora(LocalDateTime.of(2026, 12, 25, 17, 30));
        evento.setUbicacion("Auditorio Municipal");
        evento.setPrecio(20.0);
        evento.setCategoria(CategoriaEventos.CULTURA);
        evento.setUsuario(organizador);
    }

    @Test
    @DisplayName("Test de Integración → BuscarPorId()")
    public void buscarPorIdIntegrationTest(){
        //GIVEN
        Mockito.when(this.eventoRepository.findById(1)).thenReturn(Optional.of(evento));

        // THEN
        this.eventoService.getById(1);

        // WHEN
        Mockito.verify(this.eventoRepository).findById(1);
    }

    @Test
    @DisplayName("Crear un evento sin tíulo.")
    public void crearEventoNombreEnBlancoIntegrationTest(){

        // GIVEN
        EventoDTO dto = new EventoDTO();
        dto.setId(1);
        dto.setTitulo("");
        dto.setDescripcion("Evento para recaudar fondos");
        dto.setFecha(LocalDate.of(2026, 12, 25));
        dto.setHora(LocalTime.of(17, 30));
        dto.setUbicacion("Auditorio Municipal");
        dto.setPrecio(20.0);
        dto.setCategoria(CategoriaEventos.CULTURA);
        dto.setFoto("concierto.jpg");
        dto.setIdOrganizador(1);

        Usuario organizador = new Usuario();

        organizador.setId(1);
        Mockito.when(this.usuarioRepository.findById(1)).thenReturn(Optional.of(organizador));

        // THEN + WHEN
        assertThrows(IllegalArgumentException.class, () -> {
            this.eventoService.guardarEvento(dto);
        });

        Mockito.verifyNoInteractions(eventoRepository);
    }

    @Test
    @DisplayName(("Filtrar eventos por una fecha incorrecta."))
    //Given
    public void testFiltrarEventosPorFechaInvalida(){

        // GIVEN

        // THEN
        assertThrows(RuntimeException.class, () -> {
            this.eventoService.filtrarEventos("hola", CategoriaEventos.CULTURA);
        });

        // WHEN
        Mockito.verifyNoInteractions(eventoRepository);

    }

    @Test
    @DisplayName(("Obtener detalles de un evento a partir de su id."))
    public void obtenerDetallesEventoIntegrationTest() {
        // GIVEN
        Integer idBusqueda = 1;

        Mockito.when(eventoRepository.findById(idBusqueda)).thenReturn(Optional.of(evento));

        // WHEN
        EventoDTO dto = this.eventoService.getById(1);
        Mockito.verify(this.eventoRepository).findById(Mockito.anyInt());

        // THEN
        assertNotNull(dto);
        assertEquals(idBusqueda, dto.getId(), "El ID debería coincidir con el solicitado.");
    }


    @Test
    @DisplayName("Modificar información de un evento a partir de su ID.")
    public void modificarEventoIntegrationTest() {
        // GIVEN
        Integer idEvento = 1;

        EventoDTO dtoModificado = new EventoDTO();
        dtoModificado.setTitulo("Concierto Modificado");
        dtoModificado.setDescripcion("Evento para recaudar fondos");
        dtoModificado.setFecha(LocalDate.of(2026, 12, 25));
        dtoModificado.setHora(LocalTime.of(17,30));
        dtoModificado.setUbicacion("Auditorio Modificado");
        dtoModificado.setPrecio(20.0);
        dtoModificado.setCategoria(CategoriaEventos.CULTURA);

        Mockito.when(eventoRepository.findById(idEvento)).thenReturn(Optional.of(evento));
        Mockito.when(eventoRepository.save(Mockito.any(Evento.class))).thenAnswer(i -> i.getArguments()[0]);
        // WHEN
        dtoModificado.setId(idEvento);
        EventoDTO dto = eventoService.editarEvento(dtoModificado);
        // THEN
        assertNotNull(dto);

        Mockito.verify(eventoRepository).findById(idEvento);
        Mockito.verify(eventoRepository).save(Mockito.any(Evento.class));
    }

    @Test
    @DisplayName("No permitir una inscripción duplicada a un evento.")
    public void noInscripcionDuplicadoIntegrationTest() {
        // GIVEN
        Integer idUsuario = this.organizador.getId();
        Integer idEvento = 1;

        Mockito.when(usuarioRepository.findById(idUsuario)).thenReturn(Optional.of(new Usuario()));
        Mockito.when(eventoRepository.findById(idEvento)).thenReturn(Optional.of(new Evento()));

        Inscripcion inscripcionPrevia = new Inscripcion();
        Mockito.when(inscripcionRepository.findByUsuario_IdAndEvento_Id(idUsuario, idEvento)).thenReturn(inscripcionPrevia);

        // THEN + WHEN
        assertThrows(IllegalStateException.class, () -> {
            inscripcionService.inscribirUsuarioAEvento(idUsuario, idEvento, MetodoPago.EFECTIVO);
        });

        Mockito.verify(inscripcionRepository, Mockito.never()).save(Mockito.any());
    }



    @Test
    @DisplayName("Top 5 eventos con más asistentes.")
    public void top5EventosMasAsistentesIntegrationTest() {
        List<EventoTopDTO> eventos = List.of(
            new EventoTopDTO(1, "Rock Under the Stars", "Festival de rock al aire libre.", Timestamp.valueOf("2026-05-15 20:00:00"), "Estadio Olímpico",5.0, "rock.jpg", 1, 1500L),
            new EventoTopDTO(2, "Tech Future 2026", "Simposio sobre IA y Robótica.", Timestamp.valueOf("2026-06-10 09:00:00"), "Centro de Convenciones", 12.0, "tech.png", 2, 500L),
            new EventoTopDTO(3, "Secretos de la Pasta", "Taller de cocina italiana artesanal.", Timestamp.valueOf("2026-04-05 17:30:00"), "Escuela Gastronómica", 25.0, "cocina.jpg", 3, 20L),
            new EventoTopDTO(4, "Run for Hope", "Carrera de 10km a beneficio social.", Timestamp.valueOf("2026-09-20 07:00:00"), "Parque Central", 10.0, "run.webp", 4, 3000L),
            new EventoTopDTO(5, "Luces y Sombras", "Exhibición de arte digital interactivo.", Timestamp.valueOf("2026-11-12 11:00:00"), "Museo de Arte", 0.0, "arte.jpg", 5, 120L)
        );

        // GIVEN
        Mockito.when(inscripcionRepository.top5Eventos()).thenReturn(eventos);


        // WHEN
        List<EventoTopDTO> resultado = inscripcionService.obtenerTop5Eventos();

        // THEN
        assertNotNull(resultado, "La lista no debería ser nula");
        assertEquals(5, resultado.size(), "Debería devolver exactamente 5 eventos");
        assertEquals("Rock Under the Stars", resultado.get(0).getTitulo());
        assertEquals(1500, resultado.get(0).getAsistentes());
        assertEquals("Run for Hope", resultado.get(3).getTitulo());
        assertEquals("Parque Central", resultado.get(3).getUbicacion());
        assertEquals(3000, resultado.get(3).getAsistentes());

        Mockito.verify(inscripcionRepository).top5Eventos();
    }

    @Test
    @DisplayName("Subir fotos de un evento")
    public void subirFotosDeUnEventoIntegrationTest() {
        // GIVEN
        Integer idEvento = 1;
        Integer idUsuario = organizador.getId();

        FotoEventoDTO fotoInputDTO = new FotoEventoDTO();
        fotoInputDTO.setRutaFoto("nueva_foto_evento.jpg");
        fotoInputDTO.setFechaSubida(LocalDateTime.of(2026, 3, 15, 10, 0));

        FotoEvento fotoEntidadGuardada = new FotoEvento();
        fotoEntidadGuardada.setId(1);
        fotoEntidadGuardada.setRutaFoto("nueva_foto_evento.jpg");
        fotoEntidadGuardada.setFechaSubida(LocalDateTime.of(2026, 3, 15, 10, 0));
        fotoEntidadGuardada.setEvento(evento);
        fotoEntidadGuardada.setUsuario(new Usuario());

        Mockito.when(eventoRepository.findById(idEvento)).thenReturn(Optional.of(evento));
        Mockito.when(usuarioRepository.findById(idUsuario)).thenReturn(Optional.of(new Usuario()));

        Mockito.when(fotoEventoRepository.save(Mockito.any(FotoEvento.class))).thenReturn(fotoEntidadGuardada);

        // WHEN
        FotoEventoDTO resultado = fotoEventoService.guardarFoto(idEvento, idUsuario, fotoInputDTO);

        // THEN
        Assertions.assertNotNull(resultado);
        Assertions.assertEquals("nueva_foto_evento.jpg", resultado.getRutaFoto());

        Mockito.verify(fotoEventoRepository).save(Mockito.any(FotoEvento.class));
    }

}
