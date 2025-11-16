package com.safa.safaeventosbbdd.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MeInteresaDTO {
    private Integer id;
    private UsuarioDTO usuarioDTO;
    private Date fechaGuardado;
    private EventoDTO eventoDTO;
}
