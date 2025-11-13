package com.safa.safaeventosbd.controladores;

import com.safa.safaeventosbd.modelos.Evento;
import com.safa.safaeventosbd.repositorios.EventoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/eventos")
@RequiredArgsConstructor
public class EventoController {

    private final EventoRepository eventoRepository;

    @GetMapping
    public List<Evento> getAll() {
        return eventoRepository.findAll();
    }

    @GetMapping("/{id}")
    public Evento getById(@PathVariable Long id) {
        return eventoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Evento no encontrado"));
    }

    @PostMapping
    public Evento create(@RequestBody Evento evento) {
        return eventoRepository.save(evento);
    }

    @PutMapping("/{id}")
    public Evento update(@PathVariable Long id, @RequestBody Evento evento) {
        Evento e = eventoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Evento no encontrado"));

        e.setTitulo(evento.getTitulo());
        e.setDescripcion(evento.getDescripcion());
        e.setFecha(evento.getFecha());
        e.setUbicacion(evento.getUbicacion());
        e.setPrecio(evento.getPrecio());
        e.setCategoria(evento.getCategoria());
        e.setOrganizador(evento.getOrganizador());

        return eventoRepository.save(e);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        eventoRepository.deleteById(id);
    }
}
