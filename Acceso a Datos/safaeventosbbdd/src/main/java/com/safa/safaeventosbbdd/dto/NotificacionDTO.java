package com.safa.safaeventosbbdd.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.dao.DataAccessException;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class NotificacionDTO {
    private Integer id;
    private UsuarioDTO usuarioDTO;
    private String mensaje;
    private Date fechaEnvio;
    private Boolean leido;
}
