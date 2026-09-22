package com.erp.muebleria.modules.reportes.application.useCases;

import com.erp.muebleria.modules.reportes.application.dto.ConsultaComprasDTO;
import com.erp.muebleria.modules.reportes.domain.models.ReporteCompra;
import com.erp.muebleria.modules.reportes.domain.ports.ReportesGerencialesRepositoryPort;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

@AllArgsConstructor
public class GenerarReporteComprasRangoFechaUseCase {

    private final ReportesGerencialesRepositoryPort reportesPort;

    public Page<ReporteCompra> ejecutar(ConsultaComprasDTO dto, Pageable pageable) {
        return reportesPort.obtenerComprasPorRangoDeFechas(dto.getFechaInicio(), dto.getFechaFin(), pageable);
    }
}
