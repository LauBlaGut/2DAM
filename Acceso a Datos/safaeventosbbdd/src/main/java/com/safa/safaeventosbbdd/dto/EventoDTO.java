package com.safa.safaeventosbbdd.dto;

import com.safa.safaeventosbbdd.modelos.enums.CategoriaEventos;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;



import java.time.LocalDate;
import java.time.LocalTime;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class EventoDTO {
    private Integer id;
    @NotBlank(message = "El título del evento es obligatorio.")
    private String titulo;
    @NotBlank(message = "La descripción es obligatoria.")
    private String descripcion;
    @NotNull(message = "La fecha del evento es obligatoria.")
    private LocalDate fecha;
    @NotNull(message = "La hora del evento es obligatoria.")
    private LocalTime hora;
    @NotBlank(message = "La ubicación es obligatoria.")
    private String ubicacion;
    @NotNull(message = "El precio es obligatorio.")
    @PositiveOrZero(message = "El precio no puede ser negativo.")
    private Double precio;
    @NotNull(message = "Debe especificar la categoría del evento.")
    private CategoriaEventos categoria;
    private String foto;
    @NotNull(message = "Debes de indicar un id organizador.")
    private Integer idOrganizador;

}


