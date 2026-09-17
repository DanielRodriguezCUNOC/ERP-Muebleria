package com.erp.muebleria.modules.administracion.infrastructure.controllers;

import com.erp.muebleria.modules.administracion.application.dto.ConsultaComprasDTO;
import com.erp.muebleria.modules.administracion.application.dto.ConsultaResumenVentasDTO;
import com.erp.muebleria.modules.administracion.application.dto.ConsultaVentasPeriodoDTO;
import com.erp.muebleria.modules.administracion.application.useCases.*;
import com.erp.muebleria.modules.administracion.domain.models.*;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/admin/reportes")
@AllArgsConstructor
public class ReportesController {

    private final GenerarReporteTopClientesUseCase topClientesUseCase;
    private final GenerarReporteVentasPeriodoUseCase reporteVentasPeriodoUseCase;
    private final GenerarReporteTopProductosMasIngresosUseCase reporteTopProductosMasIngresosUseCase;
    private final GenerarResumenVentasAgrupadoUseCase reporteResumenVentasAgrupadoUseCase;
    private final GenerarHistorialMovimientosProductoUseCase historialMovimientosProductoUseCase;
    private final GenerarReporteComprasRangoFechaUseCase reporteComprasRangoFechaUseCase;
    private final GenerarReporteComprasPorProveedorUseCase reporteComprasPorProveedorUseCase;

    @GetMapping("/top-clientes")
    @PreAuthorize("hasAuthority('ADMINISTRACION')")
    public ResponseEntity<List<TopCliente>> obtenerTopClientes(){
        List<TopCliente> reporte = topClientesUseCase.ejecutar();
        return ResponseEntity.ok(reporte);
    }

    @GetMapping("/ventas-por-periodo")
    @PreAuthorize("hasAuthority('ADMINISTRACION')")
    public ResponseEntity<List<VentasPorPeriodo>> obtenerVentasPorPeriodo( ConsultaVentasPeriodoDTO dto){
        List<VentasPorPeriodo> reporte = reporteVentasPeriodoUseCase.ejecutar(dto);
        return ResponseEntity.ok(reporte);
    }

    @GetMapping("/top-productos-mas-ingresos")
    @PreAuthorize("hasAuthority('ADMINISTRACION')")
    public ResponseEntity<List<TopProductosMasIngresos>> obtenerTopProductosMasIngresos() {
        List<TopProductosMasIngresos> reporte = reporteTopProductosMasIngresosUseCase.ejecutar();
        return ResponseEntity.ok(reporte);
    }

    @GetMapping("/resumen-ventas-agrupado")
    @PreAuthorize("hasAuthority('ADMINISTRACION')")
    public ResponseEntity<List<ResumenVentasPeriodo>> obtenerResumenVentasAgrupado(ConsultaResumenVentasDTO dto) {
        List<ResumenVentasPeriodo> reporte = reporteResumenVentasAgrupadoUseCase.ejecutar(dto);
        return ResponseEntity.ok(reporte);
    }

    @GetMapping("/productos/{productoId}/movimientos")
    @PreAuthorize("hasAuthority('ADMINISTRACION')")
    public ResponseEntity<List<MovimientoProducto>> obtenerMovimientosProducto(@PathVariable Long productoId) {
        List<MovimientoProducto> reporte = historialMovimientosProductoUseCase.ejecutar(productoId);
        return ResponseEntity.ok(reporte);
    }

    @GetMapping("/compras-por-rango-de-fechas")
    @PreAuthorize("hasAuthority('ADMINISTRACION')")
    public ResponseEntity<List<ReporteCompra>> obtenerComprasPorRangoDeFechas(ConsultaComprasDTO dto) {
        List<ReporteCompra> reporte = reporteComprasRangoFechaUseCase.ejecutar(dto);
        return ResponseEntity.ok(reporte);
    }

    @GetMapping("proveedores/{proveedorId}/compras")
    @PreAuthorize("hasAuthority('ADMINISTRACION')")
    public ResponseEntity<List<ReporteCompra>> obtenerComprasPorProveedor(@PathVariable Long proveedorId) {
        List<ReporteCompra> reporte = reporteComprasPorProveedorUseCase.ejecutar(proveedorId);
        return ResponseEntity.ok(reporte);
    }
}
