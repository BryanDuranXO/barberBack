package com.example.barber.model.citas;

import org.springframework.cglib.core.Local;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface CitasRepository extends JpaRepository<CitasBean,Long> {
    boolean existsByFechaAndHora(LocalDate date, LocalTime time);
    List<CitasBean> findByFecha(LocalDate date);
    List<CitasBean> findByStatusFalse();
    List<CitasBean> findByStatusTrue();

    Optional<CitasBean> findByFechaAndHora(LocalDate date, LocalTime time);

}
