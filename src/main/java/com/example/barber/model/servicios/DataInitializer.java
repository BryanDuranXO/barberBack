package com.example.barber.model.servicios;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DataInitializer {
    @Bean
    CommandLineRunner initRoles(ServicioRepository repository) {
        return args -> {

            if (repository.count() == 0) {
                repository.save(new ServicioBean("Corte a tijera", 100.0));
                repository.save(new ServicioBean("Corte moderno", 100.0));
                repository.save(new ServicioBean("Corte clasico", 100.0));
                repository.save(new ServicioBean("Corte y barba", 200.0));
                repository.save(new ServicioBean("Barba", 100.0));

                System.out.println("✔ servicios insertados");
            } else {
                System.out.println("ℹ servicios ya existen");
            }
        };
    }
}
