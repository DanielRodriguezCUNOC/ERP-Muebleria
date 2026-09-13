package com.erp.muebleria.modules.usuarios.application.useCases.rolesPermisos;

import com.erp.muebleria.modules.usuarios.application.dto.CrearRolDTO;
import com.erp.muebleria.modules.usuarios.domain.entities.Permiso;
import com.erp.muebleria.modules.usuarios.domain.entities.Rol;
import com.erp.muebleria.modules.usuarios.domain.ports.RolRepositoryPort;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * Este caso de uso involucra:
 * Buscar permisos en la DB
 * Almacenar el nuevo rol en la DB
 */
@Getter
@Setter
public class CrearRolUseCase {

    private final RolRepositoryPort rolRepository;

    public CrearRolUseCase(RolRepositoryPort rolRepository) {
        this.rolRepository = rolRepository;
    }

    public void ejecutar (CrearRolDTO dto) {
        Rol nuevoRol = new Rol(
                null,
                dto.getNombre(),
                dto.getDescripcion()
        );

        List<Permiso> permisosAsignados = rolRepository.buscarPermisosPorIds(dto.getPermisosIds());

        for (Permiso permiso : permisosAsignados) {
            nuevoRol.agregarPermiso(permiso);
        }

        rolRepository.guardar(nuevoRol);
    }
}
