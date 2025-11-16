package com.safa.safaeventosbbdd.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FotoEventoDTO {
    private Integer id;
    private UsuarioDTO usuarioDTO;
    private EventoDTO eventoDTO;
    private String rutaFoto;
    private Date fechaSubida;
}
