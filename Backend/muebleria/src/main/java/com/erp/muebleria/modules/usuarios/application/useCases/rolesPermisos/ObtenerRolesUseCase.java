package com.erp.muebleria.modules.usuarios.application.useCases.rolesPermisos;

import com.erp.muebleria.modules.usuarios.application.dto.RolResponseDTO;
import com.erp.muebleria.modules.usuarios.domain.ports.RolRepositoryPort;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@AllArgsConstructor
public class ObtenerRolesUseCase {

    private final RolRepositoryPort rolRepositoryPort;

    @Transactional(readOnly = true)
    public List<RolResponseDTO> ejecutar() {
        return rolRepositoryPort.obtenerTodosLosRoles();
    }
}
