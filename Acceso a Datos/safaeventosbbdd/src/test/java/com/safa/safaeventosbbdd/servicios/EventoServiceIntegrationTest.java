package com.safa.safaeventosbbdd.servicios;


import com.safa.safaeventosbbdd.dto.EventoDTO;
import com.safa.safaeventosbbdd.dto.UsuarioDTO;
import com.safa.safaeventosbbdd.modelos.Evento;
import com.safa.safaeventosbbdd.modelos.Inscripcion;
import com.safa.safaeventosbbdd.modelos.Usuario;
import com.safa.safaeventosbbdd.modelos.enums.CategoriaEventos;
import com.safa.safaeventosbbdd.modelos.enums.MetodoPago;
import com.safa.safaeventosbbdd.modelos.enums.RolUsuario;
import com.safa.safaeventosbbdd.repositorios.EventoRepository;
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

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
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

    @Mock
    private InscripcionService inscripcionService;

    private Evento evento;
    private Usuario organizador;
    
    @BeforeEach
    void cargarDatos() {
        // Inicializamos los objetos comunes para no repetir código
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
    public void createEventTestNameBlankIntegrationTest(){

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
    public void getDetallesEventoIntegrationTest() {
        // GIVEN: Simulamos una entidad con datos completos
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
        Integer idUsuario = this.idOrganizadorPruebaz;
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

}
