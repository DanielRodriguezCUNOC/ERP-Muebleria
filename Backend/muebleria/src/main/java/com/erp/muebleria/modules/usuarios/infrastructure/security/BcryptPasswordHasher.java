package com.erp.muebleria.modules.usuarios.infrastructure.security;

import com.erp.muebleria.modules.usuarios.domain.ports.PasswordHasherPort;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

/**
 * Este adaptador usa el puerto para poder usar BCrypt
 */
@Component
public class BcryptPasswordHasher implements PasswordHasherPort {

    private final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
    @Override
    public String hash(String contrasena) {
        return encoder.encode(contrasena);
    }

    @Override
    public boolean verificar(String contrasena, String contrasenaHasheada) {
        return encoder.matches(contrasena, contrasenaHasheada);
    }
}
