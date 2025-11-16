package com.safa.safaeventosbbdd.dto;

import com.safa.safaeventosbbdd.modelos.enums.MetodoPago;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class InscripcionDTO {
    private Integer id;
    private UsuarioDTO usuarioDTO;
    private EventoDTO eventoDTO;
    private Boolean pagoRealizado;
    private MetodoPago metodoPago;
    private Boolean tieneCoste;
    private String foto;
    private LocalDateTime fechaHora;
}
