package com.safa.safaeventosbbdd.controladores;

import com.safa.safaeventosbbdd.dto.MeInteresaDTO;
import com.safa.safaeventosbbdd.servicios.MeInteresaService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/meinteresa")
@AllArgsConstructor
@CrossOrigin(origins = "*")
public class MeInteresaController {

    private MeInteresaService meInteresaService;

    // Obtener todos los "me interesa"
    @GetMapping("/all")
    public List<MeInteresaDTO> obtenerTodos() {
        return meInteresaService.getAll();
    }

    // Obtener todos los "me interesa" de un usuario específico
    @GetMapping("/usuario/{idUsuario}")
    public List<MeInteresaDTO> obtenerPorUsuario(@PathVariable Integer idUsuario) {
        return meInteresaService.getByUsuario(idUsuario);
    }

    // Guardar un "me interesa"
    @PostMapping("/guardar")
    public MeInteresaDTO guardar(@RequestBody MeInteresaDTO dto) {
        return meInteresaService.guardarMeInteresa(dto);
    }

    // Eliminar un "me interesa" por ID
    @DeleteMapping("/eliminar/{id}")
    public void eliminar(@PathVariable Integer id) {
        meInteresaService.eliminar(id);
    }
}

