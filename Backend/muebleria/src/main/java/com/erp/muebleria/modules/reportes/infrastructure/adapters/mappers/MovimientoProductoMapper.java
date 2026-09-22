package com.erp.muebleria.modules.reportes.infrastructure.adapters.mappers;

import com.erp.muebleria.modules.reportes.domain.models.MovimientoProducto;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class MovimientoProductoMapper implements RowMapper<MovimientoProducto> {
    @Override
    public MovimientoProducto mapRow(ResultSet rs, int rowNum) throws SQLException {
        return new MovimientoProducto(
                rs.getLong("id"),
                rs.getLong("producto_id"),
                rs.getInt("cantidad_cambio"),
                rs.getInt("existencia_resultante"),
                rs.getString("tipo_movimiento"),
                rs.getString("origen_tipo"),
                rs.getLong("origen_id"),
                rs.getTimestamp("fecha").toLocalDateTime()
        );
    }
}
