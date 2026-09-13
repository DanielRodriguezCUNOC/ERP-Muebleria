package com.erp.muebleria.modules.usuarios.domain.ports;

/**
 * Comunica a Usuarios y Administrador
 */
public interface EmpleadoValidacionPort {

    boolean validarDpiDeUsuario(Long usuarioId, String dpi);
}
