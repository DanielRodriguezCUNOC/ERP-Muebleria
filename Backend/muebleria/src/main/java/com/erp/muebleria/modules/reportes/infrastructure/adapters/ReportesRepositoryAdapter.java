package com.erp.muebleria.modules.reportes.infrastructure.adapters;

import com.erp.muebleria.modules.reportes.application.dto.OperacionEmpleadoDTO;
import com.erp.muebleria.modules.reportes.infrastructure.adapters.mappers.*;
import com.erp.muebleria.modules.reportes.domain.ports.ReportesGerencialesRepositoryPort;
import com.erp.muebleria.modules.reportes.domain.models.*;
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
    private final OperacionEmpleadoMapper operacionEmpleadoMapper;
    private final TopClienteMapper topClienteMapper;
    private final ResumenVentasPeriodoMapper resumenVentasPeriodoMapper;
    private final TopProductosMasIngresosMapper topProductosMasIngresosMapper;
    private final MovimientoProductoMapper movimientoProductoMapper;
    private final ReporteCompraMapper reporteCompraMapper;

    @Override
    public org.springframework.data.domain.Page<TopCliente> obtenerTopClientesPorMonto(org.springframework.data.domain.Pageable pageable) {
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
                """;

        int limit = pageable.getPageSize();
        int offset = (int) pageable.getOffset();
        String pagedSql = sql + " LIMIT " + limit + " OFFSET " + offset;
        java.util.List<TopCliente> content = jdbcTemplate.query(pagedSql, topClienteMapper);

        // Count total
        String countSql = "SELECT COUNT(DISTINCT c.id) FROM cliente c JOIN venta v ON c.id = v.cliente_id";
        long total = jdbcTemplate.queryForObject(countSql, Long.class);

        return new org.springframework.data.domain.PageImpl<>(content, pageable, total);
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
    public org.springframework.data.domain.Page<TopProductosMasIngresos> obtenerTopProductosMasIngresos(org.springframework.data.domain.Pageable pageable) {
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
                """;

        int limit = pageable.getPageSize();
        int offset = (int) pageable.getOffset();
        String pagedSql = sql + " LIMIT " + limit + " OFFSET " + offset;
        java.util.List<TopProductosMasIngresos> content = jdbcTemplate.query(pagedSql, topProductosMasIngresosMapper);

        String countSql = "SELECT COUNT(DISTINCT p.id) FROM producto p JOIN detalle_venta dv ON p.id = dv.producto_id JOIN venta v ON dv.venta_id = v.id";
        long total = jdbcTemplate.queryForObject(countSql, Long.class);

        return new org.springframework.data.domain.PageImpl<>(content, pageable, total);
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
        return jdbcTemplate.query(sql, resumenVentasPeriodoMapper, unidadTruncada, fechaInicio, fechaFin, unidadTruncada, unidadTruncada);
    }

    @Override
    public org.springframework.data.domain.Page<MovimientoProducto> obtenerMovimientosPorProducto(Long productoId, org.springframework.data.domain.Pageable pageable) {
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

        int limit = pageable.getPageSize();
        int offset = (int) pageable.getOffset();
        String pagedSql = sql + " LIMIT " + limit + " OFFSET " + offset;

        java.util.List<MovimientoProducto> content = jdbcTemplate.query(pagedSql, movimientoProductoMapper, productoId);
        String countSql = "SELECT COUNT(*) FROM inventario_log il JOIN inventario i ON il.inventario_id = i.id WHERE i.producto_id = ?";
        long total = jdbcTemplate.queryForObject(countSql, Long.class, productoId);

        return new org.springframework.data.domain.PageImpl<>(content, pageable, total);
    }

    @Override
    public org.springframework.data.domain.Page<ReporteCompra> obtenerComprasPorRangoDeFechas(LocalDateTime fechaInicio, LocalDateTime fechaFin, org.springframework.data.domain.Pageable pageable) {
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
        int limit = pageable.getPageSize();
        int offset = (int) pageable.getOffset();
        String pagedSql = sql + " LIMIT " + limit + " OFFSET " + offset;
        java.util.List<ReporteCompra> content = jdbcTemplate.query(pagedSql, reporteCompraMapper, fechaInicio, fechaFin);

        String countSql = "SELECT COUNT(*) FROM compra c WHERE c.fecha_compra BETWEEN ? AND ?";
        long total = jdbcTemplate.queryForObject(countSql, Long.class, fechaInicio, fechaFin);

        return new org.springframework.data.domain.PageImpl<>(content, pageable, total);
    }

    @Override
    public org.springframework.data.domain.Page<ReporteCompra> obtenerComprasPorProveedor(Long proveedorId, org.springframework.data.domain.Pageable pageable) {
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
        int limit = pageable.getPageSize();
        int offset = (int) pageable.getOffset();
        String pagedSql = sql + " LIMIT " + limit + " OFFSET " + offset;
        java.util.List<ReporteCompra> content = jdbcTemplate.query(pagedSql, reporteCompraMapper, proveedorId);

        String countSql = "SELECT COUNT(*) FROM compra c WHERE c.proveedor_id = ?";
        long total = jdbcTemplate.queryForObject(countSql, Long.class, proveedorId);

        return new org.springframework.data.domain.PageImpl<>(content, pageable, total);
    }

    @Override
    public org.springframework.data.domain.Page<OperacionEmpleadoDTO> obtenerOperacionesPorEmpleado(Long empleadoId, org.springframework.data.domain.Pageable pageable) {
        String sql = """
                SELECT 
                    id, 
                    empleado_id, 
                    accion, 
                    modulo, 
                    detalle, 
                    fecha
                FROM bitacora_operacion
                WHERE empleado_id = ?
                ORDER BY fecha DESC
                """;
        int limit = pageable.getPageSize();
        int offset = (int) pageable.getOffset();
        String pagedSql = sql + " LIMIT " + limit + " OFFSET " + offset;

        java.util.List<OperacionEmpleadoDTO> content = jdbcTemplate.query(pagedSql, operacionEmpleadoMapper, empleadoId);
        String countSql = "SELECT COUNT(*) FROM bitacora_operacion WHERE empleado_id = ?";
        long total = jdbcTemplate.queryForObject(countSql, Long.class, empleadoId);

        return new org.springframework.data.domain.PageImpl<>(content, pageable, total);
    }
}
