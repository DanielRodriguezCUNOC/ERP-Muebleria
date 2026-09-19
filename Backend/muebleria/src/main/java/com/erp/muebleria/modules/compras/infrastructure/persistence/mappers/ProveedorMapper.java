package com.erp.muebleria.modules.compras.infrastructure.persistence.mappers;

import com.erp.muebleria.modules.compras.application.dto.ProveedorResponseDTO;
import com.erp.muebleria.modules.compras.domain.entities.Proveedor;
import com.erp.muebleria.modules.compras.infrastructure.persistence.entities.ProveedorJpaEntity;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface ProveedorMapper {

    ProveedorJpaEntity toEntity(Proveedor domain);
    Proveedor toDomain(ProveedorJpaEntity entity);
    ProveedorResponseDTO toDto(Proveedor domain);
}
