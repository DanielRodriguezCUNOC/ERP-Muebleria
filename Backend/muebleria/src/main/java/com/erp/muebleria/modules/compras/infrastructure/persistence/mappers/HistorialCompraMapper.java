package com.erp.muebleria.modules.compras.infrastructure.persistence.mappers;

import com.erp.muebleria.modules.compras.application.dto.HistorialComprasResponseDTO;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface HistorialCompraMapper extends RowMapper<HistorialComprasResponseDTO> {
    @Override
    default HistorialComprasResponseDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
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
