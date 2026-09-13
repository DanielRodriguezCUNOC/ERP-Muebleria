package com.erp.muebleria.modules.usuarios.infrastructure.security;

import com.erp.muebleria.modules.usuarios.domain.entities.Usuario;
import com.erp.muebleria.modules.usuarios.domain.ports.TokenServicePort;
import org.springframework.stereotype.Component;

@Component
public class JwtTokenService implements TokenServicePort {
    @Override
    public String generarToken(Usuario usuario) {
        return "";
    }

    @Override
    public void invalidarToken(String token) {

    }
}
