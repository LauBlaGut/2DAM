package com.safa.safaeventosbbdd.controladores;

import com.safa.safaeventosbbdd.dto.EventoDTO;
import com.safa.safaeventosbbdd.modelos.Evento;
import com.safa.safaeventosbbdd.modelos.enums.CategoriaEventos;
import com.safa.safaeventosbbdd.servicios.EventoService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Locale;

@RestController
@RequestMapping("/eventos")
@AllArgsConstructor
public class EventoController {

    private EventoService eventoService;

    @GetMapping("/all")
    public ResponseEntity<?> obtenerEventos() {

        List<EventoDTO> eventos = eventoService.getAll();

        if (eventos.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("No hay eventos registrados.");
        }

        return ResponseEntity.ok(eventos);
    }

    @GetMapping("/{id}")
    public EventoDTO getById(@PathVariable Integer id) {
        return eventoService.getById(id);
    }

    @DeleteMapping("/{id}")
    public void eliminar(@PathVariable Integer id) {
        eventoService.eliminar(id);
    }

    @PostMapping
    public EventoDTO guardar(@RequestBody EventoDTO dto) {
        return eventoService.guardarEvento(dto);
    }


    @GetMapping()
    public ResponseEntity<?> listarConFiltros(
            @RequestParam(required = false) String fecha,
            @RequestParam(required = false) CategoriaEventos categoria) {


        List<EventoDTO> eventos = eventoService.filtrarEventos(fecha, categoria);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(eventos);
    }


    @GetMapping("/descubre")
    public List<EventoDTO> obtenerProximosEventos() {
        return eventoService.getProximosEventos();
    }


    @PutMapping("/{id}")
    public EventoDTO actualizarEvento(@PathVariable Integer id, @RequestBody EventoDTO eventoDTO) {
        eventoDTO.setId(id);
        return eventoService.editarEvento(eventoDTO);
    }

}