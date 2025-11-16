package com.safa.safaeventosbbdd.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor

public class ComentarioEventoDTO {
    private Integer id;
    private UsuarioDTO usuarioDTO;
    private EventoDTO eventoDTO;
    private String comentario;
    private Date fechaComentario;
}
