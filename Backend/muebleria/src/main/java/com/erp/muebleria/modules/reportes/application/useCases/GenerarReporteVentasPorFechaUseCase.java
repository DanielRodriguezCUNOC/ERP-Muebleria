package com.erp.muebleria.modules.reportes.application.useCases;

import com.erp.muebleria.modules.reportes.application.dto.ConsultaVentasPeriodoDTO;
import com.erp.muebleria.modules.reportes.domain.models.VentasPorPeriodo;
import com.erp.muebleria.modules.reportes.domain.ports.ReportesGerencialesRepositoryPort;
import lombok.AllArgsConstructor;

import java.util.List;

/**
 * Caso de uso para generar un reporte de ventas por periodo de tiempo definido.
 */
@AllArgsConstructor
public class GenerarReporteVentasPorFechaUseCase {

    private final ReportesGerencialesRepositoryPort reportesPort;

    public List<VentasPorPeriodo> ejecutar(ConsultaVentasPeriodoDTO dto){
        //* Obtenemos las ventas por periodo de tiempo definido
        return reportesPort.obtenerVentasPorPeriodo(dto.getFechaInicio(), dto.getFechaFin());
    }
}
