package com.safa.safaeventosbbdd.controladores;

import com.safa.safaeventosbbdd.dto.PerfilDTO;
import com.safa.safaeventosbbdd.servicios.PerfilService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/perfiles")
@AllArgsConstructor
@CrossOrigin(origins = "*")
public class PerfilController {

    private PerfilService perfilService;

    @GetMapping("/all")
    public List<PerfilDTO> obtenerPerfiles() {
        return perfilService.getAll();
    }

    @GetMapping("/{id}")
    public PerfilDTO getById(@PathVariable Integer id) {
        return perfilService.getById(id);
    }

    @PostMapping
    public PerfilDTO guardar(@RequestBody PerfilDTO dto) {
        return perfilService.guardarPerfil(dto);
    }

    @DeleteMapping()
    public void eliminar(@RequestParam Integer id) {
        perfilService.eliminar(id);
    }
}