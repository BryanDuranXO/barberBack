package com.example.barber.model.usuarios;

import com.example.barber.model.rol.RolBean;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UsuarioRepository extends JpaRepository<UsuarioBean, Long> {

    Optional<UsuarioBean> findByUsuario(String username);
    List<UsuarioBean> findByRol_Id(Long id);
}
