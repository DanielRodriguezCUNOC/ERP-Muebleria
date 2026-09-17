package com.erp.muebleria.modules.administracion.application.useCases;

import com.erp.muebleria.modules.administracion.application.dto.ConsultaResumenVentasDTO;
import com.erp.muebleria.modules.administracion.domain.models.ResumenVentasPeriodo;
import com.erp.muebleria.modules.administracion.domain.ports.ReportesGerencialesRepositoryPort;
import lombok.AllArgsConstructor;

import java.util.List;

@AllArgsConstructor
public class GenerarResumenVentasAgrupadoUseCase {

    private final ReportesGerencialesRepositoryPort reportesPort;

    public List<ResumenVentasPeriodo> ejecutar(ConsultaResumenVentasDTO dto){
        String agrupacion = (dto.getAgrupacion() != null) ? dto.getAgrupacion().toUpperCase() : "DIA";
        return reportesPort.obtenerResumenVentasAgrupadoPorPeriodo(dto.getFechaInicio(), dto.getFechaFin(), agrupacion);
    }
}
