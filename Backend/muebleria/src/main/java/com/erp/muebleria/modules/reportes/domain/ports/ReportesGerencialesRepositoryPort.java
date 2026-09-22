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
    org.springframework.data.domain.Page<TopCliente> obtenerTopClientesPorMonto(org.springframework.data.domain.Pageable pageable);
    List<VentasPorPeriodo> obtenerVentasPorPeriodo(LocalDateTime fechaInicio, LocalDateTime fechaFin);
    org.springframework.data.domain.Page<TopProductosMasIngresos> obtenerTopProductosMasIngresos(org.springframework.data.domain.Pageable pageable);
    List<ResumenVentasPeriodo> obtenerResumenVentasAgrupadoPorPeriodo(LocalDateTime fechaInicio, LocalDateTime fechaFin, String agrupacion);
    org.springframework.data.domain.Page<MovimientoProducto> obtenerMovimientosPorProducto(Long productoId, org.springframework.data.domain.Pageable pageable);
    org.springframework.data.domain.Page<ReporteCompra> obtenerComprasPorRangoDeFechas(LocalDateTime fechaInicio, LocalDateTime fechaFin, org.springframework.data.domain.Pageable pageable);
    org.springframework.data.domain.Page<ReporteCompra> obtenerComprasPorProveedor(Long proveedorId, org.springframework.data.domain.Pageable pageable);
    org.springframework.data.domain.Page<OperacionEmpleadoDTO> obtenerOperacionesPorEmpleado(Long empleadoId, org.springframework.data.domain.Pageable pageable);
}
