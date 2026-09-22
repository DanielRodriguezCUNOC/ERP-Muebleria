package com.erp.muebleria.modules.reportes.infrastructure.adapters.mappers;

import com.erp.muebleria.modules.reportes.application.dto.OperacionEmpleadoDTO;
import org.springframework.stereotype.Component;

import org.springframework.jdbc.core.RowMapper;
import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class OperacionEmpleadoMapper implements RowMapper<OperacionEmpleadoDTO> {

    @Override
    public OperacionEmpleadoDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
        return new OperacionEmpleadoDTO(
                rs.getLong("id"),
                rs.getLong("empleado_id"),
                rs.getString("accion"),
                rs.getString("modulo"),
                rs.getString("detalle"),
                rs.getTimestamp("fecha").toLocalDateTime()
        );
    }
}
