package com.erp.muebleria.modules.administracion.application.useCases;

import com.erp.muebleria.modules.administracion.application.dto.ConsultaResumenVentasDTO;
import com.erp.muebleria.modules.administracion.application.dto.ConsultaVentasPeriodoDTO;
import com.erp.muebleria.modules.administracion.domain.models.ResumenVentasPeriodo;
import com.erp.muebleria.modules.administracion.domain.models.VentasPorPeriodo;
import com.erp.muebleria.modules.administracion.domain.ports.ReportesGerencialesRepositoryPort;
import lombok.AllArgsConstructor;

import java.util.List;

/**
 * Caso de uso para generar un reporte de ventas por periodo de tiempo definido.
 */
@AllArgsConstructor
public class GenerarReporteVentasPeriodoUseCase {

    private final ReportesGerencialesRepositoryPort reportesPort;

    public List<VentasPorPeriodo> ejecutar(ConsultaVentasPeriodoDTO dto){
        //* Obtenemos las ventas por periodo de tiempo definido
        return reportesPort.obtenerVentasPorPeriodo(dto.getFechaInicio(), dto.getFechaFin());
    }
}
