package com.erp.muebleria.modules.compras.infrastructure.persistence.mappers;

import com.erp.muebleria.modules.compras.domain.entities.Compra;
import com.erp.muebleria.modules.compras.domain.entities.DetalleCompra;
import com.erp.muebleria.modules.compras.infrastructure.persistence.entities.CompraJpaEntity;
import com.erp.muebleria.modules.compras.infrastructure.persistence.entities.DetalleCompraIdJpaEntity;
import com.erp.muebleria.modules.compras.infrastructure.persistence.entities.DetalleCompraIdJpaEntity;
import com.erp.muebleria.modules.compras.infrastructure.persistence.entities.DetalleCompraJpaEntity;

import java.util.HashSet;
import java.util.stream.Collectors;

public class CompraMapper {

    public static CompraJpaEntity toEntity(Compra domain) {
        CompraJpaEntity entity = new CompraJpaEntity();
        entity.setEmpleadoId(domain.getEmpleadoId());
        entity.setFechaCompra(domain.getFechaCompra());
        entity.setProveedorIds(domain.getProveedorIds() != null ? domain.getProveedorIds() : new HashSet<>());

        if (domain.getDetalles() != null) {
            entity.setDetalles(domain.getDetalles().stream().map(detalle -> {
                DetalleCompraJpaEntity detalleEntity = new DetalleCompraJpaEntity();
                detalleEntity.setId(new DetalleCompraIdJpaEntity(null, detalle.getProductoId()));
                detalleEntity.setCompra(entity);
                detalleEntity.setProveedorId(detalle.getProveedorId());
                detalleEntity.setCantidad(detalle.getCantidad());
                detalleEntity.setPrecioUnitario(detalle.getPrecioUnitario());
                detalleEntity.setCostoTotal(detalle.getCostoTotal());
                return detalleEntity;
            }).collect(Collectors.toList()));
        }
        return entity;
    }

    public static Compra toDomain(CompraJpaEntity entity) {
        Compra domain = new Compra();
        domain.setId(entity.getId());
        domain.setEmpleadoId(entity.getEmpleadoId());
        domain.setFechaCompra(entity.getFechaCompra());
        domain.setProveedorIds(entity.getProveedorIds() != null ? new HashSet<>(entity.getProveedorIds()) : new HashSet<>());

        if (entity.getDetalles() != null) {
            domain.setDetalles(entity.getDetalles().stream().map(detalle -> {
                DetalleCompra detalleDomain = new DetalleCompra();
                detalleDomain.setCompraId(detalle.getId().getCompraId());
                detalleDomain.setProductoId(detalle.getId().getProductId());
                detalleDomain.setProveedorId(detalle.getProveedorId());
                detalleDomain.setCantidad(detalle.getCantidad());
                detalleDomain.setPrecioUnitario(detalle.getPrecioUnitario());
                detalleDomain.setCostoTotal(detalle.getCostoTotal());
                return detalleDomain;
            }).collect(Collectors.toList()));
        }
        return domain;
    }
}