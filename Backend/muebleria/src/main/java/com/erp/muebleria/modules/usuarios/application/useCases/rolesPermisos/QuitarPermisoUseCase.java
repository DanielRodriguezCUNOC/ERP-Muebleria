package com.erp.muebleria.modules.usuarios.application.useCases.rolesPermisos;

import com.erp.muebleria.modules.common.domain.exceptions.RecursoNoEncontradoException;
import com.erp.muebleria.modules.usuarios.domain.ports.RolRepositoryPort;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
public class QuitarPermisoUseCase {

    private final RolRepositoryPort repositoryPort;

    @Transactional
    public void ejecutar(Long rolId, Long permisoId) {
        if (!repositoryPort.existeRol((rolId)))
            throw new RecursoNoEncontradoException("El rol no existe");
        if (!repositoryPort.existePermiso((permisoId)))
            throw new RecursoNoEncontradoException("El permiso no existe");

        repositoryPort.quitarPermiso(rolId, permisoId);
    }
}
