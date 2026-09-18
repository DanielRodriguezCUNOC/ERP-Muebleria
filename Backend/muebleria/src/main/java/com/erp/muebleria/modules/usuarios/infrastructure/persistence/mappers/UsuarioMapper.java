package com.erp.muebleria.modules.usuarios.infrastructure.persistence.mappers;

import com.erp.muebleria.modules.usuarios.domain.entities.Permiso;
import com.erp.muebleria.modules.usuarios.domain.entities.Rol;
import com.erp.muebleria.modules.usuarios.domain.entities.Usuario;
import com.erp.muebleria.modules.usuarios.infrastructure.persistence.entities.PermisoJpaEntity;
import com.erp.muebleria.modules.usuarios.infrastructure.persistence.entities.RolJpaEntity;
import com.erp.muebleria.modules.usuarios.infrastructure.persistence.entities.UsuarioJpaEntity;

public class UsuarioMapper {

    public static Usuario toDomain(UsuarioJpaEntity entity) {
        if (entity == null) return null;

        Rol rolDomain = null;

        if (entity.getRol() != null) {
            rolDomain = new Rol(
                    entity.getRol().getId(),
                    entity.getRol().getRol(),
                    entity.getRol().getDescripcion()
            );

            if (entity.getRol().getPermisos() != null) {
                for (PermisoJpaEntity pEntity : entity.getRol().getPermisos()) {
                    rolDomain.agregarPermiso(new Permiso(
                            pEntity.getId(),
                            pEntity.getNombre(),
                            pEntity.getDescripcion()));
                }
            }
        }

        return new Usuario(
                entity.getId(),
                entity.getName(),
                entity.getUsuario(),
                entity.getPassword(),
                entity.getDpi(),
                entity.getNumeroTelefono(),
                entity.getActivo(),
                entity.getAreaId(),
                rolDomain
        );
    }

    public static UsuarioJpaEntity toEntity(Usuario domain) {
        if (domain == null) return null;

        RolJpaEntity rolEntity = null;
        if (domain.getRol() != null) {
            rolEntity = new RolJpaEntity();
            rolEntity.setId(domain.getRol().getId());
            rolEntity.setRol(domain.getRol().getNombre());
            rolEntity.setDescripcion(domain.getRol().getDescripcion());
        }

        UsuarioJpaEntity entity = new UsuarioJpaEntity();
        entity.setId(domain.getId());
        entity.setName(domain.getName());
        entity.setUsuario(domain.getUsuario());
        entity.setPassword(domain.getPassword());
        entity.setDpi(domain.getDpi());
        entity.setNumeroTelefono(domain.getNumeroTelefono());
        entity.setActivo(domain.getActivo());
        entity.setAreaId(domain.getAreaId());
        entity.setRol(rolEntity);

        return entity;
    }
}