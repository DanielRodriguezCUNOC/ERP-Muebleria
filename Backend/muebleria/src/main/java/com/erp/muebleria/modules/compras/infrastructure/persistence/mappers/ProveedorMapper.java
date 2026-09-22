package com.erp.muebleria.modules.compras.infrastructure.persistence.mappers;

import com.erp.muebleria.modules.compras.application.dto.ProveedorResponseDTO;
import com.erp.muebleria.modules.compras.domain.entities.Proveedor;
import com.erp.muebleria.modules.compras.infrastructure.persistence.entities.ProveedorJpaEntity;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import com.erp.muebleria.modules.compras.application.mappers.ProveedorApplicationMapper;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface ProveedorMapper extends ProveedorApplicationMapper {

    ProveedorJpaEntity toEntity(Proveedor domain);
    Proveedor toDomain(ProveedorJpaEntity entity);
    ProveedorResponseDTO toDto(Proveedor domain);
    ProveedorResponseDTO toDto(ProveedorJpaEntity entity);
}
