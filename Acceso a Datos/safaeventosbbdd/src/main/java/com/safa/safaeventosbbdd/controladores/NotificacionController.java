package com.safa.safaeventosbbdd.controladores;

import com.safa.safaeventosbbdd.dto.NotificacionDTO;
import com.safa.safaeventosbbdd.servicios.NotificacionService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RestController
@RequestMapping("/notificaciones")
@AllArgsConstructor
public class NotificacionController {

    private NotificacionService notificacionService;

    @GetMapping("/all")
    public List<NotificacionDTO> obtenerNotificaciones() {
        return notificacionService.getAll();
    }

    @GetMapping("/{id}")
    public NotificacionDTO getById(@PathVariable Integer id) {
        return notificacionService.getById(id);
    }

    @PostMapping
    public NotificacionDTO guardar(@RequestBody NotificacionDTO dto) {
        return notificacionService.guardarNotificacion(dto);
    }

    @DeleteMapping
    public void eliminar(@RequestParam Integer id) {
        notificacionService.eliminar(id);
    }
}