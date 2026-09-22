package com.erp.muebleria.modules.usuarios.infrastructure.persistence.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import com.erp.muebleria.modules.usuarios.application.dto.RolResponseDTO;
import com.erp.muebleria.modules.usuarios.domain.entities.Rol;
import com.erp.muebleria.modules.usuarios.infrastructure.persistence.entities.RolJpaEntity;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING, uses = {PermisoMapper.class})
public interface RolMapper {

    @Mapping(source = "rol", target = "nombre")
    Rol toDomain(RolJpaEntity entity);

    @Mapping(source = "nombre", target = "rol")
    RolJpaEntity toEntity(Rol domain);

    RolResponseDTO toResponseDTO(RolJpaEntity entity);

    @Mapping(source = "nombre", target = "rol")
    RolResponseDTO toResponseDTO(Rol domain);
}
