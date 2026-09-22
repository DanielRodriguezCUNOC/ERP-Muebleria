package com.erp.muebleria.modules.reportes.infrastructure.config;

import com.erp.muebleria.modules.reportes.application.useCases.*;
import com.erp.muebleria.modules.reportes.domain.ports.ReportesGerencialesRepositoryPort;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ReportesBeansConfig {

    @Bean
    public GenerarHistorialMovimientosProductoUseCase generarHistorialMovimientosProductoUseCase(
            ReportesGerencialesRepositoryPort reportesPort
    ) {
        return new GenerarHistorialMovimientosProductoUseCase(reportesPort);
    }

    @Bean
    public GenerarReporteComprasPorProveedorUseCase generarReporteComprasPorProveedorUseCase(
            ReportesGerencialesRepositoryPort reportesPort
    ) {
        return new GenerarReporteComprasPorProveedorUseCase(reportesPort);
    }

    @Bean
    public GenerarReporteComprasRangoFechaUseCase generarReporteComprasRangoFechaUseCase(
            ReportesGerencialesRepositoryPort reportesPort
    ) {
        return new GenerarReporteComprasRangoFechaUseCase(reportesPort);
    }

    @Bean
    public GenerarReporteTopClientesUseCase generarReporteTopClientesUseCase(
            ReportesGerencialesRepositoryPort reportesPort
    ) {
        return new GenerarReporteTopClientesUseCase(reportesPort);
    }

    @Bean
    public GenerarReporteTopProductosMasIngresosUseCase generarReporteTopProductosMasIngresosUseCase(
            ReportesGerencialesRepositoryPort reportesPort
    ) {
        return new GenerarReporteTopProductosMasIngresosUseCase(reportesPort);
    }

    @Bean
    public GenerarReporteVentasPorFechaUseCase generarReporteVentasPeriodoUseCase(
            ReportesGerencialesRepositoryPort reportesPort
    ) {
        return new GenerarReporteVentasPorFechaUseCase(reportesPort);
    }

    @Bean
    public GenerarResumenVentasPorPeriodoUseCase generarResumenVentasAgrupadoUseCase(
            ReportesGerencialesRepositoryPort reportesPort
    ) {
        return new GenerarResumenVentasPorPeriodoUseCase(reportesPort);
    }

    @Bean
    public ObtenerOperacionesPorEmpleadoUseCase obtenerOperacionesPorEmpleadoUseCase(
            ReportesGerencialesRepositoryPort reportesPort
    ) {
        return new ObtenerOperacionesPorEmpleadoUseCase(reportesPort);
    }

}
