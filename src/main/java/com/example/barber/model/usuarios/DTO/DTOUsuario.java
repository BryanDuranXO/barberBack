package com.example.barber.model.usuarios.DTO;

import com.example.barber.model.rol.RolBean;
import com.example.barber.model.usuarios.UsuarioBean;
import lombok.Data;

@Data
public class DTOUsuario{

    private Long id;

    private String nombre;

    private String paterno;

    private String materno;

    private String telefono;

    private String usuario;

    private String password;

    private Boolean status;

    private RolBean rol;

    public UsuarioBean toEntity(){
        return new UsuarioBean(nombre,paterno,materno,telefono,usuario,password,status,rol);
    }

    public UsuarioBean toUpsate(){
        return new UsuarioBean(nombre,paterno,materno,telefono, usuario);
    }

}
