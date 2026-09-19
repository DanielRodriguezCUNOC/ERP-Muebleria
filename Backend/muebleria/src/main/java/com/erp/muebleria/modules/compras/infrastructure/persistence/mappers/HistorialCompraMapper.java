package com.erp.muebleria.modules.compras.infrastructure.persistence.mappers;

import com.erp.muebleria.modules.compras.application.dto.HistorialComprasResponseDTO;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

@Component
public class HistorialCompraMapper implements RowMapper<HistorialComprasResponseDTO> {
    @Override
    public HistorialComprasResponseDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
        HistorialComprasResponseDTO dto = new HistorialComprasResponseDTO();
        dto.setId(rs.getLong("id"));
        Timestamp timestamp = rs.getTimestamp("fecha_compra");
        dto.setFechaCompra(timestamp !=null ? timestamp.toLocalDateTime() : null);
        dto.setEmpleadoId(rs.getLong("empleado_id"));
        dto.setTotalProductos(rs.getInt("total_productos"));
        dto.setCostoTotal(rs.getBigDecimal("costo_total"));
        dto.setProveedores(rs.getString("proveedores"));
        return dto;
    }
}
