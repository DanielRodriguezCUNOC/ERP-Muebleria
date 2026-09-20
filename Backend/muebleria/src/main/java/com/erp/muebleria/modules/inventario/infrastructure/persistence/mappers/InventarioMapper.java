package com.erp.muebleria.modules.inventario.infrastructure.persistence.mappers;

import com.erp.muebleria.modules.inventario.application.dto.ExistenciaProductoDTO;
import com.erp.muebleria.modules.inventario.domain.entities.ProductoInventario;
import com.erp.muebleria.modules.inventario.infrastructure.persistence.entities.ProductoJpaEntity;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface InventarioMapper {

    ProductoInventario toDomain(ProductoJpaEntity entity);

    default ExistenciaProductoDTO toExistenciaDto(ProductoInventario domain) {
        String estadoStock;
        if (domain.estaAgotado()) {
            estadoStock = "AGOTADO";
        } else if (domain.esBajoStock()) {
            estadoStock = "BAJO_STOCK";
        } else {
            estadoStock = "NORMAL";
        }

        return new ExistenciaProductoDTO(
                domain.getId(),
                domain.getSku(),
                domain.getNombre(),
                domain.getCategoria(),
                domain.getExistencia(),
                domain.getExistenciaMinima(),
                domain.getPrecio(),
                estadoStock
        );
    }
}