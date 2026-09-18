package com.erp.muebleria.modules.usuarios.infrastructure.persistence.mappers;

import com.erp.muebleria.modules.usuarios.domain.entities.Permiso;
import com.erp.muebleria.modules.usuarios.infrastructure.persistence.entities.PermisoJpaEntity;

public class PermisoMapper {

    public static Permiso permisoToDomain(PermisoJpaEntity entity) {
        if (entity == null) return null;
        return new Permiso(entity.getId(), entity.getNombre(), entity.getDescripcion());
    }

    public static PermisoJpaEntity permisoToEntity(Permiso domain) {
        if (domain == null) return null;
        PermisoJpaEntity entity = new PermisoJpaEntity();
        entity.setId(domain.getId());
        entity.setNombre(domain.getNombre());
        entity.setDescripcion(domain.getDescripcion());
        return entity;
    }
}
