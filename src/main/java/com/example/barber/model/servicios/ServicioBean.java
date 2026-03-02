package com.example.barber.model.servicios;

import jakarta.persistence.*;

@Entity
@Table(name = "servicios")
public class ServicioBean {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "servicio")
    private String servicio;

    @Column(name = "precio", columnDefinition = "FLOAT")
    private Double precio;

    public ServicioBean() {
    }

    public ServicioBean(Long id, String servicio, Double precio) {
        this.id = id;
        this.servicio = servicio;
        this.precio = precio;
    }

    public ServicioBean(String servicio, Double precio) {
        this.servicio = servicio;
        this.precio = precio;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getServicio() {
        return servicio;
    }

    public void setServicio(String servicio) {
        this.servicio = servicio;
    }

    public Double getPrecio() {
        return precio;
    }

    public void setPrecio(Double precio) {
        this.precio = precio;
    }
}
