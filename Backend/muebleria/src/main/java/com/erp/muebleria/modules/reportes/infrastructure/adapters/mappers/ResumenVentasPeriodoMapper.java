package com.erp.muebleria.modules.reportes.infrastructure.adapters.mappers;

import com.erp.muebleria.modules.reportes.domain.models.ResumenVentasPeriodo;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class ResumenVentasPeriodoMapper implements RowMapper<ResumenVentasPeriodo> {
    @Override
    public ResumenVentasPeriodo mapRow(ResultSet rs, int rowNum) throws SQLException {
        return new ResumenVentasPeriodo(
                rs.getString("periodo"),
                rs.getBigDecimal("ingresos_totales"),
                rs.getLong("total_facturas")
        );
    }
}
