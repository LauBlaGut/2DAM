package com.safa.safaeventosbd.controladores;

import com.safa.safaeventosbd.modelos.Notificacion;
import com.safa.safaeventosbd.repositorios.NotificacionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/notificaciones")
@RequiredArgsConstructor
public class NotificacionController {

    private final NotificacionRepository repo;

    @GetMapping
    public List<Notificacion> getAll() {
        return repo.findAll();
    }

    @PostMapping
    public Notificacion create(@RequestBody Notificacion n) {
        return repo.save(n);
    }

    @PutMapping("/{id}/leer")
    public Notificacion marcarLeida(@PathVariable Long id) {
        Notificacion n = repo.findById(id)
                .orElseThrow(() -> new RuntimeException("No encontrada"));
        n.setLeido(true);
        return repo.save(n);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        repo.deleteById(id);
    }
}
