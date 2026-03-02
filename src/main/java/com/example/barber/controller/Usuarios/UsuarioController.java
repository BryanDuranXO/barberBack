package com.example.barber.controller.Usuarios;

import com.example.barber.config.ApiResponse;
import com.example.barber.controller.Usuarios.DTO.ChangePasswordDTO;
import com.example.barber.model.usuarios.DTO.DTOUsuario;
import com.example.barber.service.Usuarios.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping( "/api/barber/usuarios")
@CrossOrigin(origins = {"*"})
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    @GetMapping("/")
    public ResponseEntity<ApiResponse> getUsuarios(){
        return usuarioService.findAll();
    }

    @GetMapping("/rol/{rol}")
    public ResponseEntity<ApiResponse> getRol(@PathVariable Long rol){
        return usuarioService.findByRol(rol);
    }

    @PostMapping("/save")
    public ResponseEntity<ApiResponse> save(@RequestBody DTOUsuario dtoUsuario){
        return usuarioService.SaveBarber(dtoUsuario.toEntity());
    }

    @PutMapping("/edit/{id}")
    public ResponseEntity<ApiResponse> update(@PathVariable Long id, @RequestBody DTOUsuario dtoUsuario){
        return usuarioService.UpdateBarber(id,dtoUsuario.toUpsate());
    }

    @PatchMapping("/{id}")
    public ResponseEntity<ApiResponse> change(@PathVariable Long id){
        return usuarioService.changeStatus(id);
    }

    @PutMapping("/new-password")
    public ResponseEntity<ApiResponse> changePassword(@RequestBody ChangePasswordDTO dto){
        return usuarioService.changePassword(dto.getUsername(), dto.getOldPassword(),dto.getNewPassword());
    }

    @DeleteMapping("/eliminar/{id}")
    public ResponseEntity<ApiResponse> eliminar(@PathVariable Long id){
        return usuarioService.deleteUsuario(id);
    }
}
