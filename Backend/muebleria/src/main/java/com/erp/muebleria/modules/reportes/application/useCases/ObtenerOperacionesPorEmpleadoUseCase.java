package com.erp.muebleria.modules.reportes.application.useCases;

import com.erp.muebleria.modules.reportes.application.dto.OperacionEmpleadoDTO;
import com.erp.muebleria.modules.reportes.domain.ports.ReportesGerencialesRepositoryPort;
import lombok.AllArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@AllArgsConstructor
public class ObtenerOperacionesPorEmpleadoUseCase {

    private final ReportesGerencialesRepositoryPort repositoryPort;


    @Transactional(readOnly = true)
    public List<OperacionEmpleadoDTO> ejecutar(Long empleadoId) {
        return repositoryPort.obtenerOperacionesPorEmpleado(empleadoId);
    }
}
