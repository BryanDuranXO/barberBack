package com.example.barber.model.usuarios;

import com.example.barber.model.rol.RolBean;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

@Entity
@Table(name = "usuarios")
public class UsuarioBean {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nombre")
    private String nombre;

    @Column(name = "paterno")
    private String paterno;

    @Column(name = "materno")
    private String materno;

    @Column(name = "telefono")
    private String telefono;

    @Column(name = "usuario")
    private String usuario;

    @Column(name = "password", columnDefinition = "TEXT")
    private String password;

    @Column(name = "status", columnDefinition = "BOOL")
    private Boolean status;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_rol", nullable = false)
    private RolBean rol;

    public UsuarioBean() {
    }

    public UsuarioBean(Long id, String nombre, String paterno, String materno, String telefono, String usuario, String password, Boolean status, RolBean rol) {
        this.id = id;
        this.nombre = nombre;
        this.paterno = paterno;
        this.materno = materno;
        this.telefono = telefono;
        this.usuario = usuario;
        this.password = password;
        this.status = status;
        this.rol = rol;
    }

    public UsuarioBean(String nombre, String paterno, String materno, String telefono) {
        this.nombre = nombre;
        this.paterno = paterno;
        this.materno = materno;
        this.telefono = telefono;
    }

    public UsuarioBean(String nombre, String paterno, String materno, String telefono, String usuario) {
        this.nombre = nombre;
        this.paterno = paterno;
        this.materno = materno;
        this.telefono = telefono;
        this.usuario = usuario;
    }

    public UsuarioBean(String nombre, String paterno, String materno, String telefono, String usuario, String password, Boolean status, RolBean rol) {
        this.nombre = nombre;
        this.paterno = paterno;
        this.materno = materno;
        this.telefono = telefono;
        this.usuario = usuario;
        this.password = password;
        this.status = status;
        this.rol = rol;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getPaterno() {
        return paterno;
    }

    public void setPaterno(String paterno) {
        this.paterno = paterno;
    }

    public String getMaterno() {
        return materno;
    }

    public void setMaterno(String materno) {
        this.materno = materno;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public RolBean getRol() {
        return rol;
    }

    public void setRol(RolBean rol) {
        this.rol = rol;
    }
}
