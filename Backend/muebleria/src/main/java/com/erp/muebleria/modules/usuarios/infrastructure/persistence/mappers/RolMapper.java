package com.erp.muebleria.modules.usuarios.infrastructure.persistence.mappers;

import com.erp.muebleria.modules.usuarios.application.dto.RolResponseDTO;
import com.erp.muebleria.modules.usuarios.domain.entities.Rol;
import com.erp.muebleria.modules.usuarios.infrastructure.persistence.entities.RolJpaEntity;

import java.util.stream.Collectors;

public class RolMapper {

    public static Rol toDomain(RolJpaEntity entity) {
        if (entity == null) return null;

        Rol rol = new Rol(
                entity.getId(),
                entity.getRol(),
                entity.getDescripcion(),
                entity.getActivo()
        );

        if (entity.getPermisos() != null) {
            entity.getPermisos().forEach(p ->
                    rol.agregarPermiso(PermisoMapper.permisoToDomain(p))
            );
        }

        return rol;
    }

    public static RolJpaEntity toEntity(Rol domain) {
        if (domain == null) return null;

        RolJpaEntity entity = new RolJpaEntity();
        entity.setId(domain.getId());
        entity.setRol(domain.getNombre());
        entity.setDescripcion(domain.getDescripcion());
        entity.setActivo(domain.getActivo());

        if (domain.getPermisos() != null) {
            entity.setPermisos(
                    domain.getPermisos().stream()
                            .map(PermisoMapper::permisoToEntity)
                            .collect(Collectors.toSet())
            );
        }

        return entity;
    }

    public static RolResponseDTO toResponseDTO(RolJpaEntity entity) {
        if (entity == null) return null;
        return new RolResponseDTO(
                entity.getId(),
                entity.getRol()
        );
    }

    public static RolResponseDTO toResponseDTO(Rol domain) {
        if (domain == null) return null;
        return new RolResponseDTO(
                domain.getId(),
                domain.getNombre()
        );
    }
}