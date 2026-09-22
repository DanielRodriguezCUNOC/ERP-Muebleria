package com.erp.muebleria.modules.reportes.application.useCases;

import com.erp.muebleria.modules.reportes.domain.models.TopCliente;
import com.erp.muebleria.modules.reportes.domain.ports.ReportesGerencialesRepositoryPort;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Caso de uso para generar un reporte de los clientes con más compras.
 */
@AllArgsConstructor
public class GenerarReporteTopClientesUseCase {

    private final ReportesGerencialesRepositoryPort reportesPort;

    public Page<TopCliente> ejecutar(Pageable pageable) {
        return reportesPort.obtenerTopClientesPorMonto(pageable);
    }
}
