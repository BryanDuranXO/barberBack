package com.example.barber.service.Usuarios;

import com.example.barber.config.ApiResponse;
import com.example.barber.model.rol.RolBean;
import com.example.barber.model.usuarios.DTO.DTOUsuario;
import com.example.barber.model.usuarios.UsuarioBean;
import com.example.barber.model.usuarios.UsuarioRepository;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

@Transactional
@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository repository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Transactional(readOnly = true)
    public ResponseEntity<ApiResponse> findAll(){
        List<UsuarioBean> usuarios = repository.findAll();
        return new ResponseEntity<>(new ApiResponse(usuarios, HttpStatus.OK, "Retornando todos los usuarios"), HttpStatus.OK);
    }

    @Transactional(readOnly = true)
    public ResponseEntity<ApiResponse> findByRol(Long id){
        List<UsuarioBean> users = repository.findByRol_Id(id);
        int total = users.size();
        return new ResponseEntity<>(new ApiResponse(total, HttpStatus.OK, "mostrando todas las citas"), HttpStatus.OK);
    }

    @Transactional(rollbackFor = {SQLException.class})
    public ResponseEntity<ApiResponse> SaveBarber(UsuarioBean usuario){
        Optional<UsuarioBean> found = repository.findByUsuario(usuario.getUsuario());

        if(found.isPresent()){
            return new ResponseEntity<>(new ApiResponse (HttpStatus.BAD_REQUEST, "Usuario existente", true), HttpStatus.BAD_REQUEST);
        }

        usuario.setPassword(passwordEncoder.encode(usuario.getPassword()));
        repository.saveAndFlush(usuario);

        return new ResponseEntity<>(new ApiResponse(HttpStatus.CREATED, "Barbero registrado correctamente", false), HttpStatus.CREATED);
    }

    @Transactional(rollbackFor = {SQLException.class})
    public ResponseEntity<ApiResponse> UpdateBarber(Long id, UsuarioBean usuario){
        Optional<UsuarioBean> found = repository.findById(id);

        if(found.isPresent()){
            UsuarioBean usuarioBean = found.get();
            usuarioBean.setNombre(usuario.getNombre());
            usuarioBean.setPaterno(usuario.getPaterno());
            usuarioBean.setMaterno(usuario.getMaterno());
            usuarioBean.setTelefono(usuario.getTelefono());
            usuarioBean.setUsuario(usuario.getUsuario());

            repository.save(usuarioBean);
            return new ResponseEntity<>(new ApiResponse(HttpStatus.OK, "Barbero actualizado correctamente", false), HttpStatus.OK);

        }

        return new ResponseEntity<>(new ApiResponse(HttpStatus.NOT_FOUND, "Barbero no encontrado", true), HttpStatus.NOT_FOUND);
    }

    @Transactional(rollbackFor = {SQLException.class})
    public ResponseEntity<ApiResponse> changeStatus(Long id){
        Optional<UsuarioBean> found = repository.findById(id);

        if(found.isPresent()){
            UsuarioBean usuarioBean = found.get();

            if(usuarioBean.getStatus()){
                usuarioBean.setStatus(false);
                repository.save(usuarioBean);
                return new ResponseEntity<>(new ApiResponse(HttpStatus.OK, "Barbero actualizado correctamente", false), HttpStatus.OK);
            } else{
                usuarioBean.setStatus(true);
                repository.save(usuarioBean);
                return new ResponseEntity<>(new ApiResponse(HttpStatus.OK, "Barbero actualizado correctamente", false), HttpStatus.OK);

            }

        }

        return new ResponseEntity<>(new ApiResponse(HttpStatus.NOT_FOUND, "Barbero no encontrado", true), HttpStatus.NOT_FOUND);
    }


    @Transactional(rollbackFor = {SQLException.class})
    public ResponseEntity<ApiResponse> changePassword(String username, String oldPassword, String newPassword){
        Optional<UsuarioBean> optional = repository.findByUsuario(username);

        if(optional.isPresent()){
            UsuarioBean usuarioBean = optional.get();
            if(passwordEncoder.matches(oldPassword, usuarioBean.getPassword())){
                usuarioBean.setPassword(passwordEncoder.encode(newPassword));
                repository.save(usuarioBean);
            }

            return new ResponseEntity<>(new ApiResponse(HttpStatus.OK,"Contraseña actualizada correctamente", false ), HttpStatus.OK);
        }

        return new ResponseEntity<>(new ApiResponse(HttpStatus.NOT_FOUND,"Usuario no encontrado", true ), HttpStatus.NOT_FOUND);

    }

    @Transactional(rollbackFor = {SQLException.class})
    public ResponseEntity<ApiResponse> deleteUsuario(Long id){
        Optional<UsuarioBean> found = repository.findById(id);
        if(found.isPresent()){

            if(found.get().getId() == 1L){
                return new ResponseEntity<>(new ApiResponse(HttpStatus.CONFLICT,"Este usuario no se puede eliminar", true ), HttpStatus.CONFLICT);

            }

            repository.deleteById(id);
            return new ResponseEntity<>(new ApiResponse(HttpStatus.OK,"Usuario eliminadi correctamente", false ), HttpStatus.OK);

        }

        return new ResponseEntity<>(new ApiResponse(HttpStatus.NOT_FOUND,"Usuario no encontrado", true ), HttpStatus.NOT_FOUND);

    }



}
