package com.safa.safaeventosbbdd.dto;

import com.safa.safaeventosbbdd.modelos.enums.RolUsuario;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UsuarioDTO {
    private Integer id;
    @NotBlank(message = "El correo es obligatorio.")
    private String email;
    @NotBlank(message = "La contraseña es obligatoria.")
    private String contrasenia;
    private RolUsuario rolUsuario;
    private Boolean verificacion;
}
