package com.erp.muebleria.modules.usuarios.infrastructure.persistence;

import com.erp.muebleria.modules.usuarios.domain.entities.Permiso;
import com.erp.muebleria.modules.usuarios.domain.entities.Rol;
import com.erp.muebleria.modules.usuarios.domain.ports.RolRepositoryPort;

import java.util.List;
import java.util.Optional;

public class PostgresRolRepository implements RolRepositoryPort {
    @Override
    public void guardar(Rol rol) {

    }

    @Override
    public Optional<Rol> buscarPorDpi(Long id) {
        return Optional.empty();
    }

    @Override
    public Optional<Permiso> buscarPermisoPorId(Long id) {
        return Optional.empty();
    }

    @Override
    public List<Permiso> buscarPermisosPorIds(List<Long> ids) {
        return List.of();
    }
}
