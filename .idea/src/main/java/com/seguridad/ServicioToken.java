package com.seguridad;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.Claims;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class ServicioToken {
    private static final String CLAVE_SECRETA = "clave-secreta";
    private static final long DURACION = 1000 * 60 * 60; // 1 hora

    public String generarToken(String usuario) {
        return Jwts.builder()
                .setSubject(usuario)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + DURACION))
                .signWith(SignatureAlgorithm.HS256, CLAVE_SECRETA)
                .compact();
    }

    public String obtenerUsuario(String token) {
        Claims claims = Jwts.parser()
                .setSigningKey(CLAVE_SECRETA)
                .parseClaimsJws(token)
                .getBody();
        return claims.getSubject();
    }

    public boolean validarToken(String token) {
        try {
            obtenerUsuario(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}

