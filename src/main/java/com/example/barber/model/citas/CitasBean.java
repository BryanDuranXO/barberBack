package com.example.barber.model.citas;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Entity
@Table(name = "citas")
public class CitasBean {
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

    @Column(name = "fecha", columnDefinition = "DATE")
    private LocalDate fecha;

    @Column(name = "status", columnDefinition = "BOOL")
    private Boolean status;

    @Column(name = "barbero")
    private String barbero;

    @Column(name = "hora", columnDefinition = "TIME")
    private LocalTime hora;

    @Column(name = "servicios", length = 255)
    private String servicios;

    @Column(name = "monto", columnDefinition = "FLOAT")
    private Double monto;

    @Column(name = "escaneada", columnDefinition = "BOOLEAN")
    private Boolean escaneada;

    @Column(name = "escaneadaD", columnDefinition = "DATE")
    private LocalDate escaneadaD;

    @Column(name = "escaneadaH", columnDefinition = "TIME")
    private LocalTime escaneadaH;



    public CitasBean() {
    }

    public CitasBean(Long id, String nombre, String paterno, String materno, String telefono, LocalDate fecha, Boolean status, String barbero, LocalTime hora, String servicios, Double monto, Boolean escaneada, LocalDate escaneadaD, LocalTime escaneadaH) {
        this.id = id;
        this.nombre = nombre;
        this.paterno = paterno;
        this.materno = materno;
        this.telefono = telefono;
        this.fecha = fecha;
        this.status = status;
        this.barbero = barbero;
        this.hora = hora;
        this.servicios = servicios;
        this.monto = monto;
        this.escaneada = escaneada;
        this.escaneadaD = escaneadaD;
        this.escaneadaH = escaneadaH;
    }

    public CitasBean(String nombre, String paterno, String materno, String telefono, LocalDate fecha, Boolean status, String barbero, LocalTime hora, String servicios, Double monto) {
        this.nombre = nombre;
        this.paterno = paterno;
        this.materno = materno;
        this.telefono = telefono;
        this.fecha = fecha;
        this.status = status;
        this.barbero = barbero;
        this.hora = hora;
        this.servicios = servicios;
        this.monto = monto;
    }

    public CitasBean(String nombre, String paterno, String materno, String telefono, LocalDate fecha, Boolean status, String barbero, LocalTime hora, String servicios, Double monto, Boolean escaneada, LocalDate escaneadaD, LocalTime escaneadaH) {
        this.nombre = nombre;
        this.paterno = paterno;
        this.materno = materno;
        this.telefono = telefono;
        this.fecha = fecha;
        this.status = status;
        this.barbero = barbero;
        this.hora = hora;
        this.servicios = servicios;
        this.monto = monto;
        this.escaneada = escaneada;
        this.escaneadaD = escaneadaD;
        this.escaneadaH = escaneadaH;
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

    public LocalDate getFecha() {
        return fecha;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public String getBarbero() {
        return barbero;
    }

    public void setBarbero(String barbero) {
        this.barbero = barbero;
    }

    public LocalTime getHora() {
        return hora;
    }

    public void setHora(LocalTime hora) {
        this.hora = hora;
    }

    public String getServicios() {
        return servicios;
    }

    public void setServicios(String servicios) {
        this.servicios = servicios;
    }

    public Double getMonto() {
        return monto;
    }

    public void setMonto(Double monto) {
        this.monto = monto;
    }

    public Boolean getEscaneada() {
        return escaneada;
    }

    public void setEscaneada(Boolean escaneada) {
        this.escaneada = escaneada;
    }

    public LocalDate getEscaneadaD() {
        return escaneadaD;
    }

    public void setEscaneadaD(LocalDate escaneadaD) {
        this.escaneadaD = escaneadaD;
    }

    public LocalTime getEscaneadaH() {
        return escaneadaH;
    }

    public void setEscaneadaH(LocalTime escaneadaH) {
        this.escaneadaH = escaneadaH;
    }
}
