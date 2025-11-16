package com.safa.safaeventosbbdd.controladores;

import com.safa.safaeventosbbdd.dto.ComentarioEventoDTO;
import com.safa.safaeventosbbdd.servicios.ComentarioEventoService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/comentarioevento")
public class ComentarioEventoController {

    @Autowired
    private ComentarioEventoService comentarioEventoService;

    // Obtener todos los comentarios
    @GetMapping("/all")
    public List<ComentarioEventoDTO> obtenerComentarios() {
        return comentarioEventoService.getAll();
    }

    // Obtener comentarios de un usuario
    @GetMapping("/usuario/{id}")
    public List<ComentarioEventoDTO> getByUsuario(@PathVariable Integer id) {
        return comentarioEventoService.getByUsuario(id);
    }

    // Obtener comentarios de un evento
    @GetMapping("/evento/{id}")
    public List<ComentarioEventoDTO> getByEvento(@PathVariable Integer id) {
        return comentarioEventoService.getByEvento(id);
    }

    // Guardar un comentario
    @PostMapping("/guardar")
    public ComentarioEventoDTO guardar(@RequestBody ComentarioEventoDTO dto) {
        return comentarioEventoService.guardarComentario(dto);
    }

    // Eliminar un comentario por ID
    @DeleteMapping("/eliminar/{id}")
    public void eliminar(@PathVariable Integer id) {
        comentarioEventoService.eliminar(id);
    }
}
