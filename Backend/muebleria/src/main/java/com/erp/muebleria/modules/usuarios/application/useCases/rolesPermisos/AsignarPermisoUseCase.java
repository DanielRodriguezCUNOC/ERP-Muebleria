package com.erp.muebleria.modules.usuarios.application.useCases.rolesPermisos;

import com.erp.muebleria.modules.usuarios.domain.entities.Permiso;
import com.erp.muebleria.modules.usuarios.domain.entities.Rol;
import com.erp.muebleria.modules.usuarios.domain.ports.RolRepositoryPort;

public class AsignarPermisoUseCase {

    private final RolRepositoryPort rolRepository;

    public AsignarPermisoUseCase(RolRepositoryPort rolRepository) {
        this.rolRepository = rolRepository;
    }

    public void ejecutar (Long rolId, Long permisoId) {
        Rol rol = rolRepository.buscarPorId(rolId).
                orElseThrow (() -> new RuntimeException(" El rol no existe"));

        Permiso permiso = rolRepository.buscarPermisoPorId(permisoId).
                orElseThrow (() -> new RuntimeException("El permiso no existe"));

        rol.agregarPermiso(permiso);

        rolRepository.guardar(rol);
    }
}
