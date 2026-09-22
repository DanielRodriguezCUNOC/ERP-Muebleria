package com.erp.muebleria.modules.usuarios.domain.ports;

import com.erp.muebleria.modules.usuarios.application.dto.RolResponseDTO;
import com.erp.muebleria.modules.usuarios.domain.entities.Permiso;
import com.erp.muebleria.modules.usuarios.domain.entities.Rol;

import java.util.List;
import java.util.Optional;

/**
 * Getiona persistencia y busqueda de roles y permisos
 */
public interface RolRepositoryPort {

    void guardar(Rol rol);
    Optional<Rol> buscarPorId(Long id);
    Optional<Permiso> buscarPermisoPorId(Long id);
    List<Permiso>  buscarPermisosPorIds(List<Long> ids);
    boolean tieneUsuariosAsociados(Long rolId);
    void quitarPermiso(Long rolId, Long permisoId);
    boolean existeRol(Long rolId);
    boolean existePermiso(Long permisoId);
    List<RolResponseDTO> obtenerTodosLosRoles();
}
