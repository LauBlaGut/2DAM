package com.safa.safaeventosbd.controladores;

import com.safa.safaeventosbd.modelos.FotoEvento;
import com.safa.safaeventosbd.repositorios.FotoEventoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/fotos")
@RequiredArgsConstructor
public class FotoEventoController {

    private final FotoEventoRepository repo;

    @GetMapping
    public List<FotoEvento> getAll() {
        return repo.findAll();
    }

    @PostMapping
    public FotoEvento create(@RequestBody FotoEvento foto) {
        return repo.save(foto);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        repo.deleteById(id);
    }
}