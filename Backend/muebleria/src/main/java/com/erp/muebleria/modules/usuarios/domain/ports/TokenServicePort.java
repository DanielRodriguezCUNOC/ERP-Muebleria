package com.erp.muebleria.modules.usuarios.domain.ports;

import com.erp.muebleria.modules.usuarios.domain.entities.Usuario;

/**
 * Genera y gestiona JWT
 */
public interface TokenServicePort {
    String generarToken(Usuario usuario);
    void invalidarToken(String token);
}
