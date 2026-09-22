package com.erp.muebleria.modules.reportes.application.useCases;

import com.erp.muebleria.modules.reportes.domain.models.TopProductosMasIngresos;
import com.erp.muebleria.modules.reportes.domain.ports.ReportesGerencialesRepositoryPort;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

@AllArgsConstructor
public class GenerarReporteTopProductosMasIngresosUseCase {

    private final ReportesGerencialesRepositoryPort reportesPort;

    public Page<TopProductosMasIngresos> ejecutar(Pageable pageable) {
        return reportesPort.obtenerTopProductosMasIngresos(pageable);
    }
}
