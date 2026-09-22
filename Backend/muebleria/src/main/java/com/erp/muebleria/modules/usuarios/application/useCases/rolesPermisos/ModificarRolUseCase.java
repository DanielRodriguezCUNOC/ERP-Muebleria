package com.erp.muebleria.modules.usuarios.application.useCases.rolesPermisos;

import com.erp.muebleria.modules.common.domain.exceptions.RecursoNoEncontradoException;
import com.erp.muebleria.modules.usuarios.application.dto.ModificarRolDTO;
import com.erp.muebleria.modules.usuarios.domain.entities.Rol;
import com.erp.muebleria.modules.usuarios.domain.ports.RolRepositoryPort;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class ModificarRolUseCase {

    private final RolRepositoryPort rolRepositoryPort;

    public void ejecutar(Long rolId, ModificarRolDTO dto) {
        Rol rol = rolRepositoryPort.buscarPorId(rolId)
                .orElseThrow(() -> new RecursoNoEncontradoException("Rol no encontrado con el ID: " + rolId));

        rol.actualizarInformacion(dto.getNombre(), dto.getDescripcion());
        rolRepositoryPort.guardar(rol);
    }
}
