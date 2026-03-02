package com.example.barber.model.usuarios;

import com.example.barber.model.rol.RolBean;
import com.example.barber.model.rol.RolRepository;
import com.example.barber.model.servicios.ServicioBean;
import com.example.barber.model.servicios.ServicioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.core.annotation.Order;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class UserInitializer {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private RolRepository rolRepository;
    @Bean
    CommandLineRunner initData(RolRepository rolRepository,
                               UsuarioRepository usuarioRepository) {
        return args -> {

            if (rolRepository.count() == 0) {
                rolRepository.save(new RolBean("ADMIN"));
                rolRepository.save(new RolBean("BARBER"));
            }

            if (usuarioRepository.count() == 0) {

                RolBean adminRol = rolRepository
                        .findByNombre("ADMIN")
                        .orElseThrow();

                usuarioRepository.save(
                        new UsuarioBean(
                                "Cloe",
                                "x",
                                "y",
                                "7776222548",
                                "admin",
                                passwordEncoder.encode("12345"),
                                true,
                                adminRol
                        )
                );
            }
        };
    }
}
