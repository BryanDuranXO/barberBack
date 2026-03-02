package com.example.barber.controller.Servicios;

import com.example.barber.config.ApiResponse;
import com.example.barber.service.Servicios.ServiciosService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/servicios")
@CrossOrigin(origins = {"*"})
public class ServiciosController {

    @Autowired
    private ServiciosService serviciosService;

    @GetMapping("/")
    public ResponseEntity<ApiResponse> findAll() {
        return serviciosService.findServices();
    }
}
