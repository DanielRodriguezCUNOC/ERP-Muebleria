package com.erp.muebleria.modules.compras.infrastructure.persistence.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import com.erp.muebleria.modules.compras.domain.entities.Compra;
import com.erp.muebleria.modules.compras.infrastructure.persistence.entities.CompraJpaEntity;
import com.erp.muebleria.modules.compras.domain.entities.DetalleCompra;
import com.erp.muebleria.modules.compras.infrastructure.persistence.entities.DetalleCompraJpaEntity;
import java.util.HashSet;
import org.mapstruct.AfterMapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface CompraMapper {

    CompraJpaEntity toEntity(Compra domain);

    Compra toDomain(CompraJpaEntity entity);

    @AfterMapping
    default void setCompraInDetalles(@MappingTarget CompraJpaEntity entity, Compra domain) {
        if (domain.getProveedorIds() == null) {
            entity.setProveedorIds(new HashSet<>());
        }
        if (entity.getDetalles() != null) {
            for (DetalleCompraJpaEntity detalle : entity.getDetalles()) {
                detalle.setCompra(entity);
            }
        }
    }

    @Mapping(target = "id", expression = "java(new DetalleCompraIdJpaEntity(null, domain.getProductoId()))")
    @Mapping(target = "compra", ignore = true)
    DetalleCompraJpaEntity detalleCompraToEntity(DetalleCompra domain);

    @Mapping(target = "compraId", source = "id.compraId")
    @Mapping(target = "productoId", source = "id.productoId")
    DetalleCompra detalleCompraToDomain(DetalleCompraJpaEntity entity);
}
