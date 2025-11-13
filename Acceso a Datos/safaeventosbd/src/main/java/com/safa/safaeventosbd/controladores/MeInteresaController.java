package com.safa.safaeventosbd.controladores;

import com.safa.safaeventosbd.modelos.MeInteresa;
import com.safa.safaeventosbd.repositorios.MeInteresaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/intereses")
@RequiredArgsConstructor
public class MeInteresaController {

    private final MeInteresaRepository repo;

    @GetMapping
    public List<MeInteresa> getAll() {
        return repo.findAll();
    }

    @PostMapping
    public MeInteresa create(@RequestBody MeInteresa meInteresa) {
        return repo.save(meInteresa);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        repo.deleteById(id);
    }
}
