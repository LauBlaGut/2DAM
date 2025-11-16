package com.safa.safaeventosbbdd.controladores;

import com.safa.safaeventosbbdd.dto.EventoDTO;
import com.safa.safaeventosbbdd.modelos.Evento;
import com.safa.safaeventosbbdd.servicios.EventoService;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/eventos")
@AllArgsConstructor
public class EventoController {

    private EventoService eventoService;

    @GetMapping("/all")
    public List<EventoDTO> obtenerEventos() {
        return eventoService.getAll();
    }

    @GetMapping("/{id}")
    public EventoDTO getById(@PathVariable Integer id) {
        return eventoService.getById(id);
    }

    @DeleteMapping()
    public void eliminar(@RequestParam Integer id) {
        eventoService.eliminar(id);
    }

    @PostMapping
    public EventoDTO guardar(@RequestBody EventoDTO dto) {
        return eventoService.guardarEvento(dto);
    }

}
