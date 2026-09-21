package com.erp.muebleria.modules.reportes.infrastructure.controllers;

import com.erp.muebleria.modules.reportes.application.dto.ConsultaComprasDTO;
import com.erp.muebleria.modules.reportes.application.dto.ConsultaResumenVentasDTO;
import com.erp.muebleria.modules.reportes.application.dto.ConsultaVentasPeriodoDTO;
import com.erp.muebleria.modules.reportes.application.dto.OperacionEmpleadoDTO;
import com.erp.muebleria.modules.reportes.application.useCases.*;
import com.erp.muebleria.modules.reportes.domain.models.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/reportes")
@Tag(name = "Reportes", description = "Gestión de reportes y consultas del sistema")
@SecurityRequirement(name = "BearerAuth")
@AllArgsConstructor
public class ReportesController {

    private final GenerarReporteTopClientesUseCase topClientesUseCase;
    private final GenerarReporteVentasPorFechaUseCase reporteVentasPeriodoUseCase;
    private final GenerarReporteTopProductosMasIngresosUseCase reporteTopProductosMasIngresosUseCase;
    private final GenerarResumenVentasPorPeriodoUseCase reporteResumenVentasAgrupadoUseCase;
    private final GenerarHistorialMovimientosProductoUseCase historialMovimientosProductoUseCase;
    private final GenerarReporteComprasRangoFechaUseCase reporteComprasRangoFechaUseCase;
    private final GenerarReporteComprasPorProveedorUseCase reporteComprasPorProveedorUseCase;
    private final ObtenerOperacionesPorEmpleadoUseCase obtenerOperacionesPorEmpleadoUseCase;

    @GetMapping("/top-clientes")
    @PreAuthorize("hasAnyAuthority('REPORTES_VER', 'VENTAS_GESTIONAR')")
    @Operation(summary = "Obtener top clientes", description = "Retorna una lista con los clientes que mas compras han realizado, ordenados por monto acumulado.")
    public ResponseEntity<List<TopCliente>> obtenerTopClientes(){
        List<TopCliente> reporte = topClientesUseCase.ejecutar();
        return ResponseEntity.ok(reporte);
    }

    @GetMapping("/ventas-por-rango-de-fechas")
    @PreAuthorize("hasAnyAuthority('REPORTES_VER', 'VENTAS_GESTIONAR')")
    @Operation(summary = "Obtener ventas por rango de fechas", description = "Retorna una lista con las ventas realizadas en un rango de fechas determinado.")
    public ResponseEntity<List<VentasPorPeriodo>> obtenerVentasPorRangoFecha( ConsultaVentasPeriodoDTO dto){
        List<VentasPorPeriodo> reporte = reporteVentasPeriodoUseCase.ejecutar(dto);
        return ResponseEntity.ok(reporte);
    }

    @GetMapping("/top-productos-mas-ingresos")
    @PreAuthorize("hasAnyAuthority('VENTAS_GESTIONAR')")
    @Operation(summary = "Obtener top productos por ingresos", description = "Retorna una lista con los productos que mas ingresos han generado, ordenados por monto acumulado.")
    public ResponseEntity<List<TopProductosMasIngresos>> obtenerTopProductosMasIngresos() {
        List<TopProductosMasIngresos> reporte = reporteTopProductosMasIngresosUseCase.ejecutar();
        return ResponseEntity.ok(reporte);
    }
    
    @GetMapping("/resumen-ventas-por-periodo")
    @PreAuthorize("hasAnyAuthority('REPORTES_VER', 'VENTAS_GESTIONAR')")
    @Operation(summary = "Obtener resumen de ventas por periodo", description = "Retorna un resumen de las ventas agrupadas por un periodo determinado.")
    public ResponseEntity<List<ResumenVentasPeriodo>> obtenerResumenVentasPorPeriodo(ConsultaResumenVentasDTO dto) {
        List<ResumenVentasPeriodo> reporte = reporteResumenVentasAgrupadoUseCase.ejecutar(dto);
        return ResponseEntity.ok(reporte);
    }

    @GetMapping("/productos/{productoId}/movimientos")
    @PreAuthorize("hasAnyAuthority('REPORTES_VER', 'INVENTARIO_GESTIONAR', 'COMPRAS_VER')")
    @Operation(
            summary = "Obtener movimientos de un producto",
            description = "Obtiene un reporte de los movimientos de un producto específico, incluyendo entradas y salidas, con detalles de cada movimiento."
    )
    public ResponseEntity<List<MovimientoProducto>> obtenerMovimientosProducto(@PathVariable Long productoId) {
        List<MovimientoProducto> reporte = historialMovimientosProductoUseCase.ejecutar(productoId);
        return ResponseEntity.ok(reporte);
    }

    @GetMapping("/compras-por-rango-de-fechas")
    @PreAuthorize("hasAnyAuthority('REPORTES_VER', 'COMPRAS_GESTIONAR')")
    @Operation(
            summary = "Obtener compras por rango de fechas",
            description = "Obtiene un reporte de compras realizadas en un rango de fechas específico, incluyendo detalles de cada compra y el monto total gastado."
    )
    public ResponseEntity<List<ReporteCompra>> obtenerComprasPorRangoDeFechas(ConsultaComprasDTO dto) {
        List<ReporteCompra> reporte = reporteComprasRangoFechaUseCase.ejecutar(dto);
        return ResponseEntity.ok(reporte);
    }

    @GetMapping("proveedores/{proveedorId}/compras")
    @PreAuthorize("hasAnyAuthority('REPORTES_VER', 'COMPRAS_GESTIONAR')")
    @Operation(
            summary = "Obtener compras por proveedor",
            description = "Obtiene un reporte de compras realizadas a un proveedor específico, incluyendo detalles de cada compra y el monto total gastado."
    )
    public ResponseEntity<List<ReporteCompra>> obtenerComprasPorProveedor(@PathVariable Long proveedorId) {
        List<ReporteCompra> reporte = reporteComprasPorProveedorUseCase.ejecutar(proveedorId);
        return ResponseEntity.ok(reporte);
    }

    @GetMapping("/empleados/{empleadoId}/operaciones")
    @PreAuthorize("hasAuthority('USUARIOS_VER')")
    @Operation(
            summary = "Consultar operaciones por empleado",
            description = "Obtiene el historial de acciones y operaciones registradas en la bitácora por un empleado."
    )
    public ResponseEntity<List<OperacionEmpleadoDTO>> obtenerOperacionesPorEmpleado(@PathVariable Long empleadoId) {
        return ResponseEntity.ok(obtenerOperacionesPorEmpleadoUseCase.ejecutar(empleadoId));
    }
}
