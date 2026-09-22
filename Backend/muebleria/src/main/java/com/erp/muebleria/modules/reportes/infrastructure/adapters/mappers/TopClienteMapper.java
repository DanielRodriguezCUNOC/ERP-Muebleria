package com.erp.muebleria.modules.reportes.infrastructure.adapters.mappers;

import com.erp.muebleria.modules.reportes.domain.models.TopCliente;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class TopClienteMapper implements RowMapper<TopCliente> {

    @Override
    public TopCliente mapRow(ResultSet rs, int rowNum) throws SQLException {
        return new TopCliente(
                rs.getLong("cliente_id"),
                rs.getString("nombre"),
                rs.getString("nit"),
                rs.getBigDecimal("monto_total")
        );
    }
}
