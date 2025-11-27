package com.safa.safaeventosbbdd.controladores;

import com.safa.safaeventosbbdd.dto.EventoDTO;
import com.safa.safaeventosbbdd.dto.InscripcionDTO;
import com.safa.safaeventosbbdd.modelos.Inscripcion;
import com.safa.safaeventosbbdd.modelos.enums.MetodoPago;
import com.safa.safaeventosbbdd.servicios.InscripcionService;
import lombok.AllArgsConstructor;
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
    @PostMapping("/usuario/{usuario_Id}/evento/{evento_Id}")
    public ResponseEntity<?> inscribir(
            @PathVariable Integer usuario_Id,
            @PathVariable Integer evento_Id,
            @RequestParam MetodoPago metodoPago) {

        if (inscripcionService.estaInscrito(usuario_Id, evento_Id)) {
            return ResponseEntity.ok("El usuario ya está inscrito en este evento.");
        }

        InscripcionDTO dto = inscripcionService.inscribirUsuarioAEvento(usuario_Id, evento_Id, metodoPago);

        return ResponseEntity.ok(dto);
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
}