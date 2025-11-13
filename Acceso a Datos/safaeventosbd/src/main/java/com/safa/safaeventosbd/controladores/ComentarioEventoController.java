package com.safa.safaeventosbd.controladores;

import com.safa.safaeventosbd.modelos.ComentarioEvento;
import com.safa.safaeventosbd.repositorios.ComentarioEventoRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/comentarios")
public class ComentarioEventoController {

    private final ComentarioEventoRepository repo;

    public ComentarioEventoController(ComentarioEventoRepository repo) {
        this.repo = repo;
    }

    @GetMapping
    public List<ComentarioEvento> getAll() {
        return repo.findAll();
    }
}