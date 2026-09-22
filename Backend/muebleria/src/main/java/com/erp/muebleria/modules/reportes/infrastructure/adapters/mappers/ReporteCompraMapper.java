package com.erp.muebleria.modules.reportes.infrastructure.adapters.mappers;

import com.erp.muebleria.modules.reportes.domain.models.ReporteCompra;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class ReporteCompraMapper implements RowMapper<ReporteCompra> {
    @Override
    public ReporteCompra mapRow(ResultSet rs, int rowNum) throws SQLException {
        return new ReporteCompra(
                rs.getLong("compra_id"),
                rs.getLong("proveedor_id"),
                rs.getString("nombre_proveedor"),
                rs.getTimestamp("fecha").toLocalDateTime(),
                rs.getBigDecimal("total"),
                rs.getString("estado")
        );
    }
}
