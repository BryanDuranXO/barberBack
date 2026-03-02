package com.example.barber.controller.Citas.DTO;

import lombok.Data;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
public class CitasAsignadasDTO {


    private boolean escaneada;

    private String barbero;

    private LocalDate fecha;

    private LocalTime hora;
}
