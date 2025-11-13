package com.safa.safaeventosbd.controladores;

import com.safa.safaeventosbd.modelos.Perfil;
import com.safa.safaeventosbd.repositorios.PerfilRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/perfiles")
@RequiredArgsConstructor
public class PerfilController {

    private final PerfilRepository perfilRepository;

    @GetMapping
    public List<Perfil> getAll() {
        return perfilRepository.findAll();
    }

    @GetMapping("/{id}")
    public Perfil getById(@PathVariable Long id) {
        return perfilRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Perfil no encontrado"));
    }

    @PostMapping
    public Perfil create(@RequestBody Perfil perfil) {
        return perfilRepository.save(perfil);
    }

    @PutMapping("/{id}")
    public Perfil update(@PathVariable Long id, @RequestBody Perfil perfil) {
        Perfil p = perfilRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Perfil no encontrado"));

        p.setUsuario(perfil.getUsuario());
        p.setNombre(perfil.getNombre());
        p.setApellidos(perfil.getApellidos());
        p.setCurso(perfil.getCurso());
        p.setFechaRegistro(perfil.getFechaRegistro());

        return perfilRepository.save(p);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        perfilRepository.deleteById(id);
    }
}