package com.erp.muebleria.modules.reportes.domain.ports;

import com.erp.muebleria.modules.reportes.application.dto.OperacionEmpleadoDTO;
import com.erp.muebleria.modules.reportes.domain.models.*;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Puerto de repositorio para obtener reportes gerenciales.
 * Define los métodos para los reportes que el administrador puede solicitar.
 */
public interface ReportesGerencialesRepositoryPort {
    List<TopCliente> obtenerTopClientesPorMonto(int limite);
    List<VentasPorPeriodo> obtenerVentasPorPeriodo(LocalDateTime fechaInicio, LocalDateTime fechaFin);
    List<TopProductosMasIngresos> obtenerTopProductosMasIngresos(int limite);
    List<ResumenVentasPeriodo> obtenerResumenVentasAgrupadoPorPeriodo(LocalDateTime fechaInicio, LocalDateTime fechaFin, String agrupacion);
    List<MovimientoProducto> obtenerMovimientosPorProducto(Long productoId);
    List<ReporteCompra> obtenerComprasPorRangoDeFechas(LocalDateTime fechaInicio, LocalDateTime fechaFin);
    List<ReporteCompra> obtenerComprasPorProveedor(Long proveedorId);
    List<OperacionEmpleadoDTO> obtenerOperacionesPorEmpleado(Long empleadoId);
}
