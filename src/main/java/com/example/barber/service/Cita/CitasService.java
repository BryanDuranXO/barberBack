package com.example.barber.service.Cita;

import com.example.barber.config.ApiResponse;
import com.example.barber.controller.Citas.DTO.CitasAsignadasDTO;
import com.example.barber.model.citas.CitasBean;
import com.example.barber.model.citas.CitasRepository;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.ByteArrayOutputStream;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.*;

@Service
@Transactional
public class CitasService {
    @Autowired
    private CitasRepository citasRepository;

    @Transactional(readOnly = true)
    public ResponseEntity<ApiResponse> getAll(){
        List<CitasBean> citas = citasRepository.findAll();
        return new ResponseEntity<>(new ApiResponse(citas, HttpStatus.OK, "mostrando todas las citas"), HttpStatus.OK);

    }

    @Transactional(readOnly = true)
    public ResponseEntity<ApiResponse> findByFecha(LocalDate date){
        List<CitasBean> citas = citasRepository.findByFecha(date);
        int total = citas.size();
        return new ResponseEntity<>(new ApiResponse(total, HttpStatus.OK, "mostrando todas las citas"), HttpStatus.OK);
    }

    @Transactional(readOnly = true)
    public ResponseEntity<ApiResponse> findByStatusFalse(){
        List<CitasBean> citas = citasRepository.findByStatusFalse();
        int total = citas.size();
        return new ResponseEntity<>(new ApiResponse(total, HttpStatus.OK, "mostrando todas las citas"), HttpStatus.OK);
    }

    @Transactional(readOnly = true)
    public ResponseEntity<ApiResponse> findByStatusTrue(){
        List<CitasBean> citas = citasRepository.findByStatusTrue();
        int total = citas.size();
        return new ResponseEntity<>(new ApiResponse(total, HttpStatus.OK, "mostrando todas las citas"), HttpStatus.OK);
    }

    @Transactional(rollbackFor = {SQLException.class})
    public byte[] createCita(CitasBean cita) throws Exception {

        // 1. Validar fecha
        if (cita.getFecha().isBefore(LocalDate.now())) {
            throw new IllegalArgumentException("La fecha no puede ser anterior a hoy");
        }

        // 2. Validar horario
        LocalTime hora = cita.getHora();
        if (hora.isBefore(LocalTime.of(9, 0)) ||
                hora.isAfter(LocalTime.of(20, 0))) {
            throw new IllegalArgumentException("Horario fuera de servicio");
        }

        // 3. Validar duplicados
        if (citasRepository.existsByFechaAndHora(cita.getFecha(), hora)) {
            throw new IllegalArgumentException(
                    "Ya existe una cita registrada para esa fecha y hora"
            );
        }

        // 4. Valores por defecto
        cita.setStatus(false);

        // 5. Guardar cita
        CitasBean citaGuardada = citasRepository.save(cita);

        // 6. Generar QR COMO IMAGEN (byte[])
        return generarQRImagen(citaGuardada);
    }

    @Transactional(rollbackFor = {SQLException.class})
    public ResponseEntity<ApiResponse> establecerCita(CitasAsignadasDTO dto){

        Optional<CitasBean> optional = citasRepository.findByFechaAndHora(dto.getFecha(), dto.getHora());

        if (optional.isPresent()) {
            CitasBean cita = optional.get();
            cita.setBarbero(dto.getBarbero());
            cita.setEscaneada(true);
            cita.setStatus(true);

            // 🔥 ASEGÚRATE DE QUE ESTOS CAMPOS ESTÁN EN EL ORDEN CORRECTO
            cita.setEscaneadaD(LocalDate.now());  // Fecha (DATE)
            cita.setEscaneadaH(LocalTime.now());  // Hora (TIME)

            citasRepository.saveAndFlush(cita); // 🔥 Usar saveAndFlush para confirmar inmediatamente

            return new ResponseEntity<>(
                    new ApiResponse(HttpStatus.OK, "Cita asignada exitosamente", true),
                    HttpStatus.OK
            );
        }

        return new ResponseEntity<>(
                new ApiResponse(HttpStatus.NOT_FOUND, "Cita no encontrada", false),
                HttpStatus.NOT_FOUND
        );
    }

    private byte[] generarQRImagen(CitasBean cita) throws Exception {

        String contenido = String.format(
                "CITA BARBERÍA\n%s %s %s\nTel: %s\nFecha: %s\nHora: %s\nServicio(s): %s\nMonto: %s",
                cita.getNombre(),
                cita.getPaterno(),
                cita.getMaterno(),
                cita.getTelefono(),
                cita.getFecha(),
                cita.getHora(),
                cita.getServicios(),
                cita.getMonto()
        );

        BitMatrix matrix = new MultiFormatWriter()
                .encode(contenido, BarcodeFormat.QR_CODE, 300, 300);

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        MatrixToImageWriter.writeToStream(matrix, "PNG", outputStream);

        return outputStream.toByteArray();
    }

    @Transactional(rollbackFor = {SQLException.class})
    public ResponseEntity<ApiResponse> deleteCita(Long id)  {
        Optional<CitasBean> optional = citasRepository.findById(id);

        if (optional.isPresent()) {
            citasRepository.deleteById(id);
            return new ResponseEntity<>(new ApiResponse( HttpStatus.OK, "cita eliminada", false), HttpStatus.OK);

        }

        return new ResponseEntity<>(new ApiResponse( HttpStatus.NOT_FOUND, "cita no encontrada", true), HttpStatus.OK);

    }
}
