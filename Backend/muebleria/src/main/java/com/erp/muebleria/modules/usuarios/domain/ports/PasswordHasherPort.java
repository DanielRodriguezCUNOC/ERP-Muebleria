package com.erp.muebleria.modules.usuarios.domain.ports;

/**
 * Permite aislar logica de encriptacion del dominio
 */
public interface PasswordHasherPort {

    String hash(String contrasena);
    boolean verificar(String contrasena, String contrasenaHasheada);
}
