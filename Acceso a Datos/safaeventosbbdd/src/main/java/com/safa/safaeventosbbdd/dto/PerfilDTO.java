package com.safa.safaeventosbbdd.dto;

import com.safa.safaeventosbbdd.modelos.enums.Curso;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PerfilDTO {
    private Integer id;
    private UsuarioDTO usuarioDTO;
    private String nombre;
    private String apellidos;
    private Curso curso;
    private Date fechaRegistro;
}
