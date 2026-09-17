package com.erp.muebleria.modules.administracion.infrastructure.adapters;

import com.erp.muebleria.modules.administracion.domain.models.*;
import com.erp.muebleria.modules.administracion.domain.ports.ReportesGerencialesRepositoryPort;
import lombok.AllArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
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

    @Override
    public List<VentasPorPeriodo> obtenerVentasPorPeriodo(LocalDateTime fechaInicio, LocalDateTime fechaFin) {
       //* Generar la consulta para obtener las ventas por periodo
        String sql = """
                SELECT  
                    COALESCE(SUM(total), 0) AS total_ingresos,
                    COUNT(id) AS total_facturas
                FROM venta
                WHERE fecha_venta BETWEEN ? AND ?
                """;

        //* Ejecutamos la consulta y mapeamos los resultados a objetos VentasPorPeriodo
        return jdbcTemplate.query(sql, (rs, rowNum) -> new VentasPorPeriodo(
                fechaInicio,
                fechaFin,
                rs.getBigDecimal("total_ingresos"),
                rs.getInt("total_facturas")
        ), fechaInicio, fechaFin);
    }

    @Override
    public List<TopProductosMasIngresos> obtenerTopProductosMasIngresos(int limite) {
        String sql = """
                SELECT
                p.id AS producto_id,
                p.nombre,
                SUM(dv.cantidad) AS cantidad_vendida,
                SUM(dv.subtotal) AS total_ingresos
                FROM producto p
                JOIN detalle_venta dv ON p.id = dv.producto_id
                JOIN venta v ON dv.venta_id = v.id
                GROUP BY p.id, p.nombre
                ORDER BY total_ingresos DESC
                LIMIT ?
                """;

        return jdbcTemplate.query(sql, (rs, rowNum) -> new TopProductosMasIngresos(
                rs.getLong("producto_id"),
                rs.getString("nombre"),
                rs.getLong("cantidad_vendida"),
                rs.getBigDecimal("total_ingresos")
        ), limite);
    }

    @Override
    public List<ResumenVentasPeriodo> obtenerResumenVentasAgrupadoPorPeriodo(LocalDateTime fechaInicio, LocalDateTime fechaFin, String agrupacion) {

        //* Determinamos la unidad de truncamiento según la agrupación solicitada
        String unidadTruncada = switch (agrupacion) {
            case "MES" -> "MONTH";
            case "AÑO" -> "YEAR";
            default -> "day";
        };

        String sql = """
               SELECT
               TO_CHAR(DAT_TRUNC(?, fecha_venta), 'YYYY-MM-DD') AS periodo,
               COALESCE(SUM(total), 0) AS total_ingresos,
               COUNT(id) AS total_facturas
               FROM venta
               WHERE fecha_venta BETWEEN ? AND ?
               GROUP BY DATE_TRUNC(?, fecha_venta)
               ORDER BY DATE_TRUNC(?, fecha_venta) ASC
                """;
        //* Ejecutamos la consulta y mapeamos los resultados a objetos ResumenVentasPeriodo, usamos la unidad de truncamiento y las fechas de inicio y fin como parámetros
        return jdbcTemplate.query(sql, (rs, rowNum) -> new ResumenVentasPeriodo(
                rs.getString("periodo"),
                rs.getBigDecimal("total_ingresos"),
                rs.getLong("total_facturas")
        ), unidadTruncada, fechaInicio, fechaFin, unidadTruncada, unidadTruncada);
    }

    @Override
    public List<MovimientoProducto> obtenerMovimientosPorProducto(Long productoId) {
        String sql = """
                SELECT 
                il.id,
                i.producto_id,
                il.cantidad_cambio AS cantidad_cambio,
                il.existencia_resultante,
                il.tipo_movimiento,
                il.origen_tipo,
                il.origen_id,
                il.creado_en AS fecha
                FROM inventario_log il
                JOIN inventario i ON il.inventario_id = i.id
                WHERE i.producto_id = ?
                ORDER BY il.creado_en DESC
                """;
        return jdbcTemplate.query(sql, (rs, rowNum) -> new MovimientoProducto(
                rs.getLong("id"),
                rs.getLong("producto_id"),
                rs.getInt("cantidad_cambio"),
                rs.getInt("existencia_resultante"),
                rs.getString("tipo_movimiento"),
                rs.getString("origen_tipo"),
                rs.getLong("origen_id"),
                rs.getTimestamp("fecha").toLocalDateTime()
        ), productoId);
    }

    @Override
    public List<ReporteCompra> obtenerComprasPorRangoDeFechas(LocalDateTime fechaInicio, LocalDateTime fechaFin) {
        String sql = """
                SELECT 
                c.id AS compra_id,
                c.proveedor_id,
                p.nombre AS nombre_proveedor,
                c.fecha_compra AS fecha,
                c.total,
                p.estado
                FROM compra c
                JOIN proveedor p ON c.proveedor_id = p.id
                WHERE c.fecha_compra BETWEEN ? AND ?
                ORDER BY c.fecha_compra DESC
                """;

        return jdbcTemplate.query(sql, (rs, rowNum) -> new ReporteCompra(
                rs.getLong("compra_id"),
                rs.getLong("proveedor_id"),
                rs.getString("nombre_proveedor"),
                rs.getTimestamp("fecha").toLocalDateTime(),
                rs.getBigDecimal("total"),
                rs.getString("estado")
        ), fechaInicio, fechaFin);
    }

    @Override
    public List<ReporteCompra> obtenerComprasPorProveedor(Long proveedorId) {
        String sql = """
                SELECT
                c.id AS compra_id,
                c.proveedor_id,
                p.nombre AS nombre_proveedor,
                c.fecha_compra AS fecha,
                c.total,
                c.estado
                FROM compra c
                JOIN proveedor p ON c.proveedor_id = p.id
                WHERE c.proveedor_id = ?
                ORDER BY c.fecha_compra DESC
                """;
        return jdbcTemplate.query(sql, (rs, rowNum) -> new ReporteCompra(
                rs.getLong("compra_id"),
                rs.getLong("proveedor_id"),
                rs.getString("nombre_proveedor"),
                rs.getTimestamp("fecha").toLocalDateTime(),
                rs.getBigDecimal("total"),
                rs.getString("estado")
        ), proveedorId);
    }
}
