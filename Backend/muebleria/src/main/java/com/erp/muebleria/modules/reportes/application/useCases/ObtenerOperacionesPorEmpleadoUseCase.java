package com.erp.muebleria.modules.reportes.application.useCases;

import com.erp.muebleria.modules.reportes.application.dto.OperacionEmpleadoDTO;
import com.erp.muebleria.modules.reportes.domain.ports.ReportesGerencialesRepositoryPort;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;

@AllArgsConstructor
public class ObtenerOperacionesPorEmpleadoUseCase {

    private final ReportesGerencialesRepositoryPort repositoryPort;


    @Transactional(readOnly = true)
    public Page<OperacionEmpleadoDTO> ejecutar(Long empleadoId, Pageable pageable) {
        return repositoryPort.obtenerOperacionesPorEmpleado(empleadoId, pageable);
    }
}
