package com.example.barber.service.auth;

import com.example.barber.config.ApiResponse;
import com.example.barber.model.usuarios.UsuarioBean;
import com.example.barber.model.usuarios.UsuarioRepository;
import com.example.barber.security.jwt.JwtProvider;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AccountExpiredException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;


@Service
@Transactional
public class AuthService {
    //private final PersonaService userService;
    private final UsuarioRepository personaRepository;
    private final JwtProvider provider;
    private final AuthenticationManager manager;

    public AuthService(UsuarioRepository personaRepository, JwtProvider provider, AuthenticationManager manager) {
        this.personaRepository = personaRepository;
        this.provider = provider;
        this.manager = manager;
    }

    @Transactional
    public ResponseEntity<ApiResponse> signIn(String usuario, String password) {
        try {
            Optional<UsuarioBean> foundUser = personaRepository.findByUsuario(usuario);
            if (foundUser.isEmpty()) {
                return new ResponseEntity<>(new ApiResponse(HttpStatus.BAD_REQUEST, "UserNotFound", true), HttpStatus.BAD_REQUEST);
            }

            UsuarioBean user = foundUser.get();

            Authentication auth = manager.authenticate(
                    new UsernamePasswordAuthenticationToken(usuario, password)
            );
            SecurityContextHolder.getContext().setAuthentication(auth);
            String token = provider.generateToken(auth);

            Map<String, Object> responseData = new HashMap<>();
            responseData.put("token", token);
            responseData.put("user", user.getUsuario());
            responseData.put("id", user.getId());
            responseData.put("nombre", user.getNombre());
            responseData.put("rol", user.getRol().getNombre());



            return new ResponseEntity<>(new ApiResponse(responseData, HttpStatus.OK, "Token generado"), HttpStatus.OK);

        } catch (Exception e) {
            e.printStackTrace();
            String message = "CredentialsMismatch";
            if (e instanceof DisabledException)
                message = "UserDisabled";
            if (e instanceof AccountExpiredException)
                message = "Expiro";
            return new ResponseEntity<>(new ApiResponse(HttpStatus.BAD_REQUEST, message, true), HttpStatus.UNAUTHORIZED);
        }
    }

}
