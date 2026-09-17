package com.erp.muebleria.modules.administracion.application.useCases;

import com.erp.muebleria.modules.administracion.application.dto.ConsultaComprasDTO;
import com.erp.muebleria.modules.administracion.domain.models.ReporteCompra;
import com.erp.muebleria.modules.administracion.domain.ports.ReportesGerencialesRepositoryPort;
import lombok.AllArgsConstructor;

import java.util.List;

@AllArgsConstructor
public class GenerarReporteComprasRangoFechaUseCase {

    private final ReportesGerencialesRepositoryPort reportesPort;

    public List<ReporteCompra> ejecutar(ConsultaComprasDTO dto) {
        return reportesPort.obtenerComprasPorRangoDeFechas(dto.getFechaInicio(), dto.getFechaFin());
    }
}
