package com.erp.muebleria.modules.usuarios.infrastructure.persistence.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import com.erp.muebleria.modules.usuarios.domain.entities.Permiso;
import com.erp.muebleria.modules.usuarios.infrastructure.persistence.entities.PermisoJpaEntity;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface PermisoMapper {
    Permiso permisoToDomain(PermisoJpaEntity entity);
    PermisoJpaEntity permisoToEntity(Permiso domain);
}
