package com.erp.muebleria.modules.usuarios.infrastructure.persistence.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import com.erp.muebleria.modules.usuarios.domain.entities.Usuario;
import com.erp.muebleria.modules.usuarios.infrastructure.persistence.entities.UsuarioJpaEntity;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING, uses = {RolMapper.class})
public interface UsuarioMapper {

    Usuario toDomain(UsuarioJpaEntity entity);

    UsuarioJpaEntity toEntity(Usuario domain);
}
