package com.erp.muebleria.modules.reportes.application.useCases;

import com.erp.muebleria.modules.reportes.domain.models.TopProductosMasIngresos;
import com.erp.muebleria.modules.reportes.domain.ports.ReportesGerencialesRepositoryPort;
import lombok.AllArgsConstructor;

import java.util.List;

@AllArgsConstructor
public class GenerarReporteTopProductosMasIngresosUseCase {

    private final ReportesGerencialesRepositoryPort reportesPort;

    public List<TopProductosMasIngresos> ejecutar() {
        return reportesPort.obtenerTopProductosMasIngresos(10);
    }
}
