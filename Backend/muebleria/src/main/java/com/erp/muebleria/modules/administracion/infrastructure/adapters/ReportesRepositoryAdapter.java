package com.erp.muebleria.modules.administracion.infrastructure.adapters;

import com.erp.muebleria.modules.administracion.domain.models.TopCliente;
import com.erp.muebleria.modules.administracion.domain.ports.ReportesGerencialesRepositoryPort;
import lombok.AllArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Adaptador para el repositorio de reportes gerenciales.
 * Implementa el puerto de infraestructura para interactuar con la base de datos.
 */
@AllArgsConstructor
@Component
public class ReportesRepositoryAdapter implements ReportesGerencialesRepositoryPort {

    private final JdbcTemplate jdbcTemplate;

    @Override
    public List<TopCliente> obtenerTopClientesPorMonto(int limite) {
        //* Creamos la consulta SQSL para obtener los clientes con más compras por monto total
        String sql = """
                SELECT 
                    c.id AS cliente_id, 
                    c.nombre, 
                    c.nit, 
                    SUM(v.total) AS monto_total
                FROM cliente c
                JOIN venta v ON c.id = v.cliente_id
                GROUP BY c.id, c.nombre, c.nit
                ORDER BY monto_total DESC
                LIMIT ?
                """;

        //* Ejecutamos la consulta y mapeamos los resultados a objetos TopCliente
        return jdbcTemplate.query(sql, (rs, rowNum) -> new TopCliente(
                rs.getLong("cliente_id"),
                rs.getString("nombre"),
                rs.getString("nit"),
                rs.getBigDecimal("monto_total")
        ), limite);
    }
}
