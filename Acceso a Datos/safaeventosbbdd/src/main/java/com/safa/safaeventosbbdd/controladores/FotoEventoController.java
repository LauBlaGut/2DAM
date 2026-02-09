package com.safa.safaeventosbbdd.controladores;

import com.safa.safaeventosbbdd.dto.FotoEventoDTO;
import com.safa.safaeventosbbdd.modelos.FotoEvento;
import com.safa.safaeventosbbdd.servicios.FotoEventoService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/fotoevento")
@AllArgsConstructor
@CrossOrigin(origins = "*")
public class FotoEventoController {

    private final FotoEventoService fotoEventoService;

    // Obtener todas las fotos
    @GetMapping("/all")
    public List<FotoEventoDTO> obtenerFotos() {
        return fotoEventoService.getAll();
    }

    // Obtener fotos de un usuario
    @GetMapping("/usuario/{id}")
    public List<FotoEventoDTO> getByUsuario(@PathVariable Integer id) {
        return fotoEventoService.getByUsuario(id);
    }

    // Obtener fotos de un evento
    @GetMapping("/eventos/{id}")
    public List<FotoEventoDTO> getByEvento(@PathVariable Integer id) {
        return fotoEventoService.getByEvento(id);
    }

    // Guardar una foto
    @PostMapping("/eventos/{evento_Id}/usuario/{usuario_Id}")
    public ResponseEntity<?> guardarFoto(
            @PathVariable Integer evento_Id,
            @PathVariable Integer usuario_Id,
            @RequestBody FotoEventoDTO dto) {

        try {
            FotoEventoDTO foto = fotoEventoService.guardarFoto(evento_Id, usuario_Id, dto);
            return ResponseEntity.ok(foto);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error al guardar la foto: " + e.getMessage());
        }
    }

    // Eliminar una foto por ID
    @DeleteMapping("/eliminar/{id}")
    public void eliminar(@PathVariable Integer id) {
        fotoEventoService.eliminar(id);
    }
}
