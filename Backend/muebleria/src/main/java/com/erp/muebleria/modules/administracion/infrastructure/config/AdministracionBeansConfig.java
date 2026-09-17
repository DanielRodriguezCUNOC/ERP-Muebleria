package com.erp.muebleria.modules.administracion.infrastructure.config;

import com.erp.muebleria.modules.administracion.application.useCases.*;
import com.erp.muebleria.modules.administracion.domain.ports.ConfiguracionSistemaRepositoryPort;
import com.erp.muebleria.modules.administracion.domain.ports.ReportesGerencialesRepositoryPort;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AdministracionBeansConfig {

    @Bean
    public ActualizarConfiguracionUseCase actualizarConfiguracionUseCase(
            ConfiguracionSistemaRepositoryPort configuracionPort
    ) {
        return new ActualizarConfiguracionUseCase(configuracionPort);
    }

    @Bean
    public GenerarReporteTopClientesUseCase generarReporteTopClientesUseCase(
            ReportesGerencialesRepositoryPort reportesPort
    ) {
        return new GenerarReporteTopClientesUseCase(reportesPort);
    }

    @Bean
    public GenerarReporteVentasPeriodoUseCase generarReporteVentasPeriodoUseCase(
            ReportesGerencialesRepositoryPort reportesPort
    ) {
        return new GenerarReporteVentasPeriodoUseCase(reportesPort);
    }

    @Bean
    public GenerarReporteTopProductosMasIngresosUseCase generarReporteTopProductosMasIngresosUseCase (
            ReportesGerencialesRepositoryPort reportesPort
    ) {
        return new GenerarReporteTopProductosMasIngresosUseCase(reportesPort);
    }

    @Bean
    public GenerarResumenVentasAgrupadoUseCase generarResumenVentasAgrupadoUseCase(
            ReportesGerencialesRepositoryPort reportesPort
    ) {
        return new GenerarResumenVentasAgrupadoUseCase(reportesPort);
    }

    @Bean
    public GenerarHistorialMovimientosProductoUseCase generarHistorialMovimientosProductoUseCase(
            ReportesGerencialesRepositoryPort reportesPort
    ) {
        return new GenerarHistorialMovimientosProductoUseCase(reportesPort);
    }

    @Bean
    public GenerarReporteComprasRangoFechaUseCase generarReporteComprasRangoFechaUseCase(
            ReportesGerencialesRepositoryPort reportesPort
    ) {
        return new GenerarReporteComprasRangoFechaUseCase(reportesPort);
    }

    @Bean
    public GenerarReporteComprasPorProveedorUseCase generarReporteComprasPorProveedorUseCase(
            ReportesGerencialesRepositoryPort reportesPort
    ) {
        return new GenerarReporteComprasPorProveedorUseCase(reportesPort);
    }
}
