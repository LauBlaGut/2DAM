package com.safa.safaeventosbbdd.controladores;

import com.safa.safaeventosbbdd.dto.EventoTopDTO;
import com.safa.safaeventosbbdd.dto.UsuarioActivoDTO;
import com.safa.safaeventosbbdd.servicios.InscripcionService;
import com.safa.safaeventosbbdd.servicios.UsuarioService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/estadisticas")
@RequiredArgsConstructor
public class EstadisticasController {

    private final InscripcionService inscripcionService;

    private final UsuarioService usuarioService;


    @GetMapping("/eventos")
    public List<EventoTopDTO> topEventos() {
        return inscripcionService.obtenerTop5Eventos();
    }


    @GetMapping("/usuarioActivo")
    public UsuarioActivoDTO usuarioMasActivo() {
        return usuarioService.obtenerUsuarioMasActivo();
    }
}

