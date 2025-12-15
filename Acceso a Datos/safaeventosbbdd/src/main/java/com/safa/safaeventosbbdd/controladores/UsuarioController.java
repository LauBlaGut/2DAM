package com.safa.safaeventosbbdd.controladores;

import com.safa.safaeventosbbdd.dto.UsuarioDTO;
import com.safa.safaeventosbbdd.servicios.UsuarioService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/usuarios")
@AllArgsConstructor
public class UsuarioController {

    private UsuarioService usuarioService;

    @GetMapping("/all")
    public List<UsuarioDTO> obtenerUsuarios() {
        return usuarioService.getAll();
    }

    @GetMapping("/{id}")
    public UsuarioDTO getById(@PathVariable Integer id) {
        return usuarioService.getById(id);
    }

    @PostMapping
    public UsuarioDTO guardar(@Valid @RequestBody UsuarioDTO dto) {
        return usuarioService.guardarUsuario(dto);
    }

    @DeleteMapping()
    public void eliminar(@RequestParam Integer id) {
        usuarioService.eliminar(id);
    }

    @GetMapping("/usuarioActivo")
    public ResponseEntity<?> usuarioActivo() {

        return ResponseEntity.ok(usuarioService.obtenerUsuarioMasActivo());
    }
}