package com.safa.safaeventosbd.dto;

import com.safa.safaeventosbd.enumerados.RolUsuario;

public record UsuarioDTO(
        Long id,
        String email,
        String contrasenia,
        RolUsuario rol,
        boolean verificacion
) {}
