package com.safa.safaeventosbd.controladores;

import com.safa.safaeventosbd.modelos.Inscripcion;
import com.safa.safaeventosbd.repositorios.InscripcionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/inscripciones")
@RequiredArgsConstructor
public class InscripcionController {

    private final InscripcionRepository inscripcionRepository;

    @GetMapping
    public List<Inscripcion> getAll() {
        return inscripcionRepository.findAll();
    }

    @GetMapping("/{id}")
    public Inscripcion getById(@PathVariable Long id) {
        return inscripcionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Inscripción no encontrada"));
    }

    @PostMapping
    public Inscripcion create(@RequestBody Inscripcion inscripcion) {
        return inscripcionRepository.save(inscripcion);
    }

    @PutMapping("/{id}")
    public Inscripcion update(@PathVariable Long id, @RequestBody Inscripcion inscripcion) {
        Inscripcion i = inscripcionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Inscripción no encontrada"));

        i.setUsuario(inscripcion.getUsuario());
        i.setEvento(inscripcion.getEvento());
        i.setMetodoPago(inscripcion.getMetodoPago());
        i.setPagoRealizado(inscripcion.isPagoRealizado());
        i.setTieneCoste(inscripcion.isTieneCoste());

        return inscripcionRepository.save(i);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        inscripcionRepository.deleteById(id);
    }
}
