package com.erp.muebleria.modules.administracion.application.useCases;

import com.erp.muebleria.modules.administracion.domain.models.TopCliente;
import com.erp.muebleria.modules.administracion.domain.ports.ReportesGerencialesRepositoryPort;
import lombok.AllArgsConstructor;

import java.util.List;

/**
 * Caso de uso para generar un reporte de los clientes con más compras.
 */
@AllArgsConstructor
public class GenerarReporteTopClientesUseCase {

    private final ReportesGerencialesRepositoryPort reportesPort;

    public List<TopCliente> ejecutar() {
        //* Obtenemos los 10 clientes con más compras por monto total
        return reportesPort.obtenerTopClientesPorMonto(10);
    }
}
