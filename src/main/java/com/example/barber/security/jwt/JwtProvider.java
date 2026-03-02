package com.example.barber.security.jwt;

import com.example.barber.model.usuarios.UsuarioBean;
import com.example.barber.model.usuarios.UsuarioRepository;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;

@Service
public class JwtProvider {
    @Value("${jwt.secret}")
    private String secret;
    @Value("${jwt.expiration}")
    private long expiration;
    private final String TOKEN_HEADER = "Authorization";
    private final String TOKEN_TYPE = "Bearer ";

    private final UsuarioRepository usuarioRepository;

    public JwtProvider(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    public String generateToken(Authentication auth) {

        UserDetails userDetails = (UserDetails) auth.getPrincipal();

        // Buscar usuario completo en BD
        UsuarioBean usuario = usuarioRepository
                .findByUsuario(userDetails.getUsername())
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        Claims claims = Jwts.claims().setSubject(userDetails.getUsername());

        claims.put("nombre", usuario.getNombre());
        claims.put("rol", usuario.getRol().getNombre());

        Date tokenCreateTime = new Date();
        Date tokenValidity = new Date(
                tokenCreateTime.getTime() + expiration * 1000
        );

        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(tokenCreateTime)
                .setExpiration(tokenValidity)
                .signWith(getSignKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    private Key getSignKey() {
        byte[] keyBytes = Decoders.BASE64.decode(secret);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    private Claims parseJwtClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(getSignKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    public Claims resolveClaims(HttpServletRequest req) {
        try {
            String token = resolveToken(req);
            if (token != null)
                return parseJwtClaims(token);
            return null;
        } catch (ExpiredJwtException e) {
            throw e;
        } catch (Exception e) {
            throw e;
        }
    }

    public String resolveToken(HttpServletRequest req) {
        String bearerToken = req.getHeader(TOKEN_HEADER);
        if (bearerToken != null && bearerToken.startsWith(TOKEN_TYPE))
            return bearerToken.replace(TOKEN_TYPE, "");
        // bearerToken.substring(TOKEN_TYPE.length());
        return null;
    }

    public boolean validateClaims(Claims claims, String token){
        try{
            parseJwtClaims(token);
            return claims.getExpiration().after(new Date());
        }catch (MalformedJwtException | UnsupportedJwtException | ExpiredJwtException e){
            e.printStackTrace();
        } catch (Exception e){
            e.printStackTrace();
        }
        return false;
    }


}
