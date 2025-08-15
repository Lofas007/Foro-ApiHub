package com.seguridad;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class ControladorAutenticacion {

    @Autowired
    private UsuarioRepositorio usuarioRepositorio;

    @Autowired
    private ServicioToken servicioToken;

    @PostMapping("/login")
    public String login(@RequestParam String usuario, @RequestParam String contrasena) {
        return usuarioRepositorio.findByNombreUsuario(usuario)
                .filter(u -> u.getContrasena().equals(contrasena))
                .map(u -> servicioToken.generarToken(usuario))
                .orElse("Usuario o contraseña incorrectos");
    }
}

