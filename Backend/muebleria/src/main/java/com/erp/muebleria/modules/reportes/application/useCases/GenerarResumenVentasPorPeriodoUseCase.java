package com.erp.muebleria.modules.reportes.application.useCases;

import com.erp.muebleria.modules.reportes.application.dto.ConsultaResumenVentasDTO;
import com.erp.muebleria.modules.reportes.domain.models.ResumenVentasPeriodo;
import com.erp.muebleria.modules.reportes.domain.ports.ReportesGerencialesRepositoryPort;
import lombok.AllArgsConstructor;

import java.util.List;

@AllArgsConstructor
public class GenerarResumenVentasPorPeriodoUseCase {

    private final ReportesGerencialesRepositoryPort reportesPort;

    public List<ResumenVentasPeriodo> ejecutar(ConsultaResumenVentasDTO dto){
        String agrupacion = (dto.getAgrupacion() != null) ? dto.getAgrupacion().toUpperCase() : "DIA";
        return reportesPort.obtenerResumenVentasAgrupadoPorPeriodo(dto.getFechaInicio(), dto.getFechaFin(), agrupacion);
    }
}
