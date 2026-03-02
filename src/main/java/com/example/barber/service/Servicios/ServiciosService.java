package com.example.barber.service.Servicios;

import com.example.barber.config.ApiResponse;
import com.example.barber.model.servicios.ServicioBean;
import com.example.barber.model.servicios.ServicioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class ServiciosService {

    @Autowired
    private ServicioRepository repository;

    @Transactional(readOnly = true)
    public ResponseEntity<ApiResponse> findServices(){
        List<ServicioBean> servicios = repository.findAll();
        return new ResponseEntity<>(new ApiResponse(servicios, HttpStatus.OK, "obteniendo todos los servicios"), HttpStatus.OK);
    }
}
