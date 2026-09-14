package com.erp.muebleria.modules.usuarios.infrastructure.persistence.mappers;

import com.erp.muebleria.modules.usuarios.domain.entities.Permiso;
import com.erp.muebleria.modules.usuarios.domain.entities.Rol;
import com.erp.muebleria.modules.usuarios.domain.entities.Usuario;
import com.erp.muebleria.modules.usuarios.infrastructure.persistence.entities.PermisoJpaEntity;
import com.erp.muebleria.modules.usuarios.infrastructure.persistence.entities.RolJpaEntity;
import com.erp.muebleria.modules.usuarios.infrastructure.persistence.entities.UsuarioJpaEntity;

/**
 * Transforma de una entidad a un POJO y viceversa
 * para no hacerlo dentro de las reglas de negocio
 */
public class UsuarioMapper {

    public static Usuario toDomain (UsuarioJpaEntity entity) {
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

        return new Usuario (
                entity.getId(),
                entity.getUsuario(),
                entity.getPassword(),
                entity.getActivo(),
                rolDomain
        );
    }

    public static UsuarioJpaEntity toEntity (Usuario domain) {
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
        entity.setUsuario(domain.getUsuario());
        entity.setPassword(domain.getPassword());
        entity.setActivo(domain.getActivo());
        entity.setRol(rolEntity);

        return entity;
    }
}
