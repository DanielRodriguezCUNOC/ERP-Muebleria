package com.erp.muebleria.modules.compras.application.mappers;

import com.erp.muebleria.modules.compras.application.dto.ProveedorResponseDTO;
import com.erp.muebleria.modules.compras.domain.entities.Proveedor;
import com.erp.muebleria.modules.compras.infrastructure.persistence.entities.ProveedorJpaEntity;

public interface ProveedorApplicationMapper {
    ProveedorJpaEntity toEntity(Proveedor domain);
    Proveedor toDomain(ProveedorJpaEntity entity);
    ProveedorResponseDTO toDto(Proveedor domain);
    ProveedorResponseDTO toDto(ProveedorJpaEntity entity);
}
