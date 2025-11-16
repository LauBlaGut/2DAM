package com.safa.safaeventosbbdd.dto;

import com.safa.safaeventosbbdd.modelos.enums.RolUsuario;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UsuarioDTO {
    private Integer id;
    private String email;
    private String contrasenia;
    private RolUsuario rolUsuario;
    private Boolean verificacion;
}
