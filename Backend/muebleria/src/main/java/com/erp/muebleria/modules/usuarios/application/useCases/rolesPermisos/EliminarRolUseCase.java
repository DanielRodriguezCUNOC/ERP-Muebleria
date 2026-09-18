package com.erp.muebleria.modules.usuarios.application.useCases.rolesPermisos;

import com.erp.muebleria.modules.common.domain.exceptions.RecursoNoEncontradoException;
import com.erp.muebleria.modules.usuarios.domain.entities.Rol;
import com.erp.muebleria.modules.usuarios.domain.ports.RolRepositoryPort;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class EliminarRolUseCase {

    private final RolRepositoryPort rolRepository;

    public void ejecutar(Long rolId) {
        Rol rol = rolRepository.buscarPorId(rolId)
                .orElseThrow(() -> new RecursoNoEncontradoException("El rol no existe"));

        if (rolRepository.tieneUsuariosAsociados(rolId)) {
            throw new RecursoNoEncontradoException("No se puede desactivar el rol porque hay empleados activos asignados a él.");
        }

        rol.desactivar();
        rolRepository.guardar(rol);
    }
}
