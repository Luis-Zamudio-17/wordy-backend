package com.wordy.wordy_backend.model;

import jakarta.persistence.*;

@Entity
@Table(name = "USUARIO")
public class Usuario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_USUARIO")
    private Long idUsuario;

    @Column(name = "NOMBRE")
    private String nombre;

    @Column(name = "NOMBRE_USUARIO")
    private String nombreUsuario;

    @Column(name = "CONTRASENA_HASH")
    private String contrasenaHash;

    @Column(name = "ID_ROL")
    private Integer idRol;

    @Column(name = "ACTIVO")
    private Integer activo;

    // Getters y Setters
    public Long getIdUsuario() { return idUsuario; }
    public void setIdUsuario(Long idUsuario) { this.idUsuario = idUsuario; }
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    public String getNombreUsuario() { return nombreUsuario; }
    public void setNombreUsuario(String nombreUsuario) { this.nombreUsuario = nombreUsuario; }
    public String getContrasenaHash() { return contrasenaHash; }
    public void setContrasenaHash(String contrasenaHash) { this.contrasenaHash = contrasenaHash; }
    public Integer getIdRol() { return idRol; }
    public void setIdRol(Integer idRol) { this.idRol = idRol; }
    public Integer getActivo() { return activo; }
    public void setActivo(Integer activo) { this.activo = activo; }
}