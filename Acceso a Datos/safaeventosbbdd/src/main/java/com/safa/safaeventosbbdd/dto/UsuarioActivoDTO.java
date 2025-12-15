package com.safa.safaeventosbbdd.dto;

public class UsuarioActivoDTO {
    private Integer id;
    private String email;
    private String nombre;
    private String apellidos;
    private Long eventosPublicados;
    private Long eventosParticipados;
    private Long totalActividad;


    public UsuarioActivoDTO(Integer id, String email, String nombre, String apellidos,
                            Long eventosPublicados, Long eventosParticipados, Long totalActividad) {
        this.id = id;
        this.email = email;
        this.nombre = nombre;
        this.apellidos = apellidos;
        this.eventosPublicados = eventosPublicados;
        this.eventosParticipados = eventosParticipados;
        this.totalActividad = totalActividad;
    }

    // Getters estándar para exponer los datos en la API
    public Integer getId() { return id; }
    public String getEmail() { return email; }
    public String getNombre() { return nombre; }
    public String getApellidos() { return apellidos; }
    public Long getEventosPublicados() { return eventosPublicados; }
    public Long getEventosParticipados() { return eventosParticipados; }
    public Long getTotalActividad() { return totalActividad; }
}
