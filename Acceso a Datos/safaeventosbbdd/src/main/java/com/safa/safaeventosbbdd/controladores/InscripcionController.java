package com.safa.safaeventosbbdd.controladores;

import com.safa.safaeventosbbdd.dto.InscripcionDTO;
import com.safa.safaeventosbbdd.servicios.InscripcionService;
import lombok.AllArgsConstructor;
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
    @GetMapping("/usuario/{id}")
    public List<InscripcionDTO> getByUsuario(@PathVariable Integer id) {
        return inscripcionService.getByUsuario(id);
    }

    // Obtener inscripciones de un evento
    @GetMapping("/evento/{id}")
    public List<InscripcionDTO> getByEvento(@PathVariable Integer id) {
        return inscripcionService.getByEvento(id);
    }

    // Guardar una nueva inscripcion
    @PostMapping("/guardar")
    public InscripcionDTO guardar(@RequestBody InscripcionDTO dto) {
        return inscripcionService.guardarInscripcion(dto);
    }

    // Eliminar una inscripcion por ID
    @DeleteMapping("/eliminar/{id}")
    public void eliminar(@PathVariable Integer id) {
        inscripcionService.eliminar(id);
    }
}