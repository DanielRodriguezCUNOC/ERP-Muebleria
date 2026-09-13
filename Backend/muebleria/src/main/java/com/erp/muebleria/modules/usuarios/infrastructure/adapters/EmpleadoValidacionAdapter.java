package com.erp.muebleria.modules.usuarios.infrastructure.adapters;

import com.erp.muebleria.modules.usuarios.domain.ports.EmpleadoValidacionPort;
import org.springframework.stereotype.Component;

/**
 * Ete adaptador resuelve regla cruzada con el modulo de Administracion
 * Consulta si el DPI coincide con el usuario.
 */
@Component
public class EmpleadoValidacionAdapter implements EmpleadoValidacionPort {
    @Override
    public boolean validarDpiDeUsuario(Long usuarioId, String dpi) {
        return false;
    }
}
