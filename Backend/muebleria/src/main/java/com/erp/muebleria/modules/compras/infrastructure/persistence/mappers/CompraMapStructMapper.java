package com.erp.muebleria.modules.compras.infrastructure.persistence.mappers;

import com.erp.muebleria.modules.compras.application.dto.CompraResponseDTO;
import com.erp.muebleria.modules.compras.domain.entities.Compra;
import com.erp.muebleria.modules.compras.domain.entities.DetalleCompra;
import com.erp.muebleria.modules.compras.infrastructure.persistence.entities.CompraJpaEntity;
import com.erp.muebleria.modules.compras.infrastructure.persistence.entities.DetalleCompraJpaEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

import java.util.ArrayList;
import java.util.List;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface CompraMapStructMapper {

    @Mapping(target = "detalles", source = "detalles")
    CompraJpaEntity toEntity(Compra domain);

    @Mapping(target = "detalles", source = "detalles")
    Compra toDomain(CompraJpaEntity entity);

    @Mapping(target = "compraId", source = "id.compraId")
    @Mapping(target = "productoId", source = "id.productoId")
    DetalleCompra toDomainDetail(DetalleCompraJpaEntity entity);

    @Mapping(target = "id.compraId", source = "compraId")
    @Mapping(target = "id.productoId", source = "productoId")
    @Mapping(target = "compra", ignore = true)
    DetalleCompraJpaEntity toEntityDetail(DetalleCompra domain);

    default CompraResponseDTO toResponseDto(Compra domain, String mensaje) {
        List<Long> proveedores = domain.getProveedorIds() != null
                ? new ArrayList<>(domain.getProveedorIds())
                : new ArrayList<>();

        return new CompraResponseDTO(
                domain.getId(),
                proveedores,
                domain.getEmpleadoId(),
                domain.getFechaCompra(),
                mensaje
        );
    }
}
