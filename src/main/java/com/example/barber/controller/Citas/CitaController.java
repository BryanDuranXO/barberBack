package com.example.barber.controller.Citas;

import com.example.barber.config.ApiResponse;
import com.example.barber.controller.Citas.DTO.CitasAsignadasDTO;
import com.example.barber.model.citas.CitasBean;
import com.example.barber.service.Cita.CitasService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/barber/citas")
@CrossOrigin(origins = {"*"})
public class CitaController {
    @Autowired
    private CitasService citasService;

    @GetMapping("/")
    public ResponseEntity<ApiResponse> listarCitas() {
        return citasService.getAll();
    }

    @GetMapping("/fecha/{date}")
    public ResponseEntity<ApiResponse> findByFecha(@PathVariable LocalDate date){
        return citasService.findByFecha(date);
    }

    @GetMapping("/status/false")
    public ResponseEntity<ApiResponse> findByStatus(){
        return citasService.findByStatusFalse();
    }


    @GetMapping("/status/true")
    public ResponseEntity<ApiResponse> findByStatusTrue(){
        return citasService.findByStatusTrue();
    }


    @PostMapping(
            value = "/",
            produces = MediaType.IMAGE_PNG_VALUE
    )
    public ResponseEntity<byte[]> registrarCita(@RequestBody CitasBean cita) {
        try {
            byte[] qrImage = citasService.createCita(cita);

            return ResponseEntity
                    .ok()
                    .contentType(MediaType.IMAGE_PNG)
                    .body(qrImage);

        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PutMapping("/scan-cita")
    public ResponseEntity<ApiResponse> scanCita(@RequestBody CitasAsignadasDTO dto) {
        return citasService.establecerCita(dto);
    }

    @DeleteMapping("/eliminar/{id}")
    public ResponseEntity<ApiResponse> eliminarCita(@PathVariable Long id) {
        return citasService.deleteCita(id);
    }

}
