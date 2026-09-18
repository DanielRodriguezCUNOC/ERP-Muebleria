package com.erp.muebleria.modules.reportes.infrastructure.adapters.mappers;

import com.erp.muebleria.modules.reportes.domain.models.TopProductosMasIngresos;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class TopProductosMasIngresosMapper implements RowMapper<TopProductosMasIngresos> {
    @Override
    public TopProductosMasIngresos mapRow(ResultSet rs, int rowNum) throws SQLException {
        return new TopProductosMasIngresos(
                rs.getLong("producto_id"),
                rs.getString("nombre"),
                rs.getLong("cantidad_vendida"),
                rs.getBigDecimal("ingresos_totales")
        );
    }
}
