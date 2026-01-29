package com.safa.safaeventosbbdd.controladores;

import com.safa.safaeventosbbdd.dto.ComentarioEventoDTO;
import com.safa.safaeventosbbdd.servicios.ComentarioEventoService;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/comentarioevento")
@CrossOrigin(origins = "*")
public class ComentarioEventoController {


    private final ComentarioEventoService comentarioEventoService;

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
    @PostMapping("/eventos/{evento_Id}/comentarios/usuario/{usuario_Id}")
    public ResponseEntity<?> guadarComentario(
            @PathVariable Integer evento_Id,
            @PathVariable Integer usuario_Id,
            @RequestBody ComentarioEventoDTO dto) {

        try {
            ComentarioEventoDTO respuesta =
                    comentarioEventoService.guardarComentario(evento_Id, usuario_Id, dto);

            return ResponseEntity.ok(respuesta);

        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error al guardar comentario: " + e.getMessage());
        }
    }

    // Eliminar un comentario por ID
    @DeleteMapping("/eliminar/{id}")
    public void eliminar(@PathVariable Integer id) {
        comentarioEventoService.eliminar(id);
    }
}
