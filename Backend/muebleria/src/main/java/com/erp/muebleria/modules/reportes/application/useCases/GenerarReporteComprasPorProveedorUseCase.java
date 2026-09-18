package com.erp.muebleria.modules.reportes.application.useCases;

import com.erp.muebleria.modules.reportes.domain.models.ReporteCompra;
import com.erp.muebleria.modules.reportes.domain.ports.ReportesGerencialesRepositoryPort;
import lombok.AllArgsConstructor;

import java.util.List;

@AllArgsConstructor
public class GenerarReporteComprasPorProveedorUseCase {

    private final ReportesGerencialesRepositoryPort reportesPort;

    public List<ReporteCompra> ejecutar(Long proveedorId) {
       return reportesPort.obtenerComprasPorProveedor(proveedorId);
    }
}
