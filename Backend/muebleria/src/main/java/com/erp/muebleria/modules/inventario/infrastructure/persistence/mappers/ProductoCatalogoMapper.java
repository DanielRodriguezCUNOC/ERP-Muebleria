package com.erp.muebleria.modules.inventario.infrastructure.persistence.mappers;

import com.erp.muebleria.modules.inventario.application.dto.ProductoCatalogoResponseDTO;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class ProductoCatalogoMapper implements RowMapper<ProductoCatalogoResponseDTO> {
    @Override
    public ProductoCatalogoResponseDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
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
