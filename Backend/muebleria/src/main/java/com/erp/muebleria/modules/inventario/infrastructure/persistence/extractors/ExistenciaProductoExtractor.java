package com.erp.muebleria.modules.inventario.infrastructure.persistence.extractors;

import com.erp.muebleria.modules.inventario.application.dto.ExistenciaProductoResponseDTO;
import com.erp.muebleria.modules.inventario.application.dto.LoteExistenciaDTO;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

@Component
public class ExistenciaProductoExtractor implements ResultSetExtractor<ExistenciaProductoResponseDTO> {

    @Override
    public ExistenciaProductoResponseDTO extractData(ResultSet rs) throws SQLException, DataAccessException {
        ExistenciaProductoResponseDTO dto = null;

        while (rs.next()) {
            if (dto == null) {
                dto = new ExistenciaProductoResponseDTO();
                dto.setProductoId(rs.getLong("producto_id"));
                dto.setSku(rs.getString("sku"));
                dto.setNombre(rs.getString("nombre"));
                dto.setExistenciaMinima(rs.getInt("existencia_minima"));
                dto.setExistenciaTotal(rs.getInt("existencia_total"));
            }

            Long loteId = rs.getObject("lote_id", Long.class);
            if (loteId != null) {
                LoteExistenciaDTO lote = new LoteExistenciaDTO();
                lote.setLoteId(loteId);
                lote.setCantidadInicial(rs.getInt("cantidad_inicial"));
                lote.setCantidadDisponible(rs.getInt("cantidad_disponible"));
                lote.setCostoUnitario(rs.getBigDecimal("costo_unitario"));

                Timestamp timestamp = rs.getTimestamp("creado_en");
                lote.setFechaIngreso(timestamp != null ? timestamp.toLocalDateTime() : null);

                dto.getLotesDisponibles().add(lote);
            }
        }
        return dto;
    }
}
