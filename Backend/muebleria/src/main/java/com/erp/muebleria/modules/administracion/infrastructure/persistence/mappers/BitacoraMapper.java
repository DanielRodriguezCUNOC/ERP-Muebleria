package com.erp.muebleria.modules.administracion.infrastructure.persistence.mappers;

import com.erp.muebleria.modules.administracion.application.dto.BitacoraResponseDTO;
import com.erp.muebleria.modules.administracion.domain.entities.BitacoraOperacion;
import com.erp.muebleria.modules.administracion.infrastructure.persistence.entities.BitacoraOperacionJpaEntity;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface BitacoraMapper {

    //* Convierte de JPA a POJO
    BitacoraOperacion toDomain(BitacoraOperacionJpaEntity entity);

    //* Convierte de POJO a JPA
    BitacoraOperacionJpaEntity toEntity(BitacoraOperacion domain);

    //* De POJO a DTO
    BitacoraResponseDTO toDto(BitacoraOperacion domain);
}
