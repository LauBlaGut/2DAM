package com.safa.safaeventosbbdd.controladores;

import com.safa.safaeventosbbdd.dto.EventoDTO;
import com.safa.safaeventosbbdd.dto.EventoTopDTO;
import com.safa.safaeventosbbdd.dto.InscripcionDTO;
import com.safa.safaeventosbbdd.modelos.Inscripcion;
import com.safa.safaeventosbbdd.modelos.enums.MetodoPago;
import com.safa.safaeventosbbdd.servicios.InscripcionService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/inscripciones")
@AllArgsConstructor
public class InscripcionController {

    private final InscripcionService inscripcionService;

    // Obtener todas las inscripciones
    @GetMapping("/all")
    public List<InscripcionDTO> obtenerInscripciones() {
        return inscripcionService.getAll();
    }

    // Obtener inscripciones de un usuario
    @GetMapping("/usuarios/{usuario_Id}/eventos")
    public ResponseEntity<?> eventosParticipa(@PathVariable Integer usuario_Id) {
        List<EventoDTO> eventos = inscripcionService.getEventos_Usuario(usuario_Id);

        if (eventos.isEmpty()) {
            return ResponseEntity.ok("El usuario no participa en ningún evento.");
        }

        return ResponseEntity.ok(eventos);
    }
    // Obtener inscripciones de un evento
    @GetMapping("/evento/{id}")
    public List<InscripcionDTO> getByEvento(@PathVariable Integer id) {
        return inscripcionService.getByEvento(id);
    }

    //Inscripción de un usuario a un evento
    @PostMapping("/eventos/{eventoId}/inscripciones")
    public ResponseEntity<?> inscribir(
            @PathVariable Integer eventoId,
            @RequestBody InscripcionDTO dto) {

        Integer usuarioId = dto.getUsuarioDTO().getId();
        MetodoPago metodoPago = dto.getMetodoPago();

        if (inscripcionService.estaInscrito(usuarioId, eventoId)) {
            return ResponseEntity.ok("El usuario ya está inscrito en este evento.");
        }

        InscripcionDTO inscripcion = inscripcionService
                .inscribirUsuarioAEvento(usuarioId, eventoId, metodoPago);

        return ResponseEntity.status(HttpStatus.CREATED).body(inscripcion);
    }

    //Pago de una inscripción
    @PatchMapping("/{inscripcion_Id}/pago")
    public ResponseEntity<?> pagar(@PathVariable Integer inscripcion_Id) {

        try {
            InscripcionDTO dto = inscripcionService.pagarInscripcion(inscripcion_Id);
            return ResponseEntity.ok(dto);

        } catch (IllegalStateException e) {
            return ResponseEntity.badRequest().body(e.getMessage());

        } catch (RuntimeException e) {
            return ResponseEntity.status(404).body(e.getMessage());
        }
    }

    // Eliminar una inscripcion por ID
    @DeleteMapping("/eliminar/{id}")
    public void eliminar(@PathVariable Integer id) {
        inscripcionService.eliminar(id);
    }


    // Obtener el top 5 de eventos con más inscripciones
    @GetMapping("/estadisticas/eventos")
    public ResponseEntity<?> obtenerTopEventos() {

        List<EventoTopDTO> top = inscripcionService.obtenerTop5Eventos();

        return ResponseEntity.ok(top);
    }
}