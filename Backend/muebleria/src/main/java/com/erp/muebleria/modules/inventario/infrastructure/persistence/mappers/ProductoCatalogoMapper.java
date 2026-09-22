package com.erp.muebleria.modules.inventario.infrastructure.persistence.mappers;

import com.erp.muebleria.modules.inventario.application.dto.ProductoCatalogoResponseDTO;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface ProductoCatalogoMapper extends RowMapper<ProductoCatalogoResponseDTO> {
    @Override
    default ProductoCatalogoResponseDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
        ProductoCatalogoResponseDTO dto = new ProductoCatalogoResponseDTO();
        dto.setId(rs.getLong("id"));
        dto.setSku(rs.getString("sku"));
        dto.setNombre(rs.getString("nombre"));
        dto.setDescripcion(rs.getString("descripcion"));
        dto.setCategoria(rs.getString("categoria"));
        dto.setPrecioVenta(rs.getBigDecimal("precio_venta"));
        dto.setExistenciaTotal(rs.getInt("existencia_total"));
        dto.setActivo(rs.getBoolean("activo"));
        return dto;
    }
}
