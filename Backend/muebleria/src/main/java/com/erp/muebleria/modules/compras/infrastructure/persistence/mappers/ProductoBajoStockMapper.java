package com.erp.muebleria.modules.compras.infrastructure.persistence.mappers;

import com.erp.muebleria.modules.compras.application.dto.ProductoBajoStockResponseDTO;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class ProductoBajoStockMapper implements RowMapper<ProductoBajoStockResponseDTO> {

    @Override
    public ProductoBajoStockResponseDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
        ProductoBajoStockResponseDTO dto = new ProductoBajoStockResponseDTO();
        dto.setProductoId(rs.getLong("producto_id"));
        dto.setSku(rs.getString("sku"));
        dto.setNombre(rs.getString("nombre"));
        dto.setCategoria(rs.getString("categoria"));
        dto.setExistenciaActual(rs.getInt("existencia_actual"));
        dto.setExistenciaMinima(rs.getInt("existencia_minima"));
        dto.setCantidadSugeridaReabastecimiento(rs.getInt("cantidad_sugerida"));
        return dto;
    }
}
