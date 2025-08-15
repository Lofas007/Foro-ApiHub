package com.seguridad;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

public class FiltroJWT extends OncePerRequestFilter {

    private final ServicioToken servicioToken;

    public FiltroJWT(ServicioToken servicioToken) {
        this.servicioToken = servicioToken;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        String header = request.getHeader("Authorization");
        if (header != null && header.startsWith("Bearer ")) {
            String token = header.substring(7);
            if (servicioToken.validarToken(token)) {
                String usuario = servicioToken.obtenerUsuario(token);
                SecurityContextHolder.getContext().setAuthentication(
                        new UsuarioAutenticacion(usuario, null, null)
                );
            }
        }
        filterChain.doFilter(request, response);
    }
}

