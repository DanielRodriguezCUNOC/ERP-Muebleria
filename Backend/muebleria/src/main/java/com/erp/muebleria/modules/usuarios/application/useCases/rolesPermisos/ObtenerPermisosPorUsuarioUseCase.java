package com.erp.muebleria.modules.usuarios.application.useCases.rolesPermisos;

import com.erp.muebleria.modules.common.domain.exceptions.RecursoNoEncontradoException;
import com.erp.muebleria.modules.usuarios.application.dto.PermisoResponseDTO;
import com.erp.muebleria.modules.usuarios.domain.ports.UsuarioRepositoryPort;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@AllArgsConstructor
public class ObtenerPermisosPorUsuarioUseCase {

    private final UsuarioRepositoryPort repositoryPort;

    @Transactional(readOnly = true)
    public List<PermisoResponseDTO> ejecutar(Long usuarioId){
        if (!repositoryPort.existeUsuario(usuarioId))
            throw new RecursoNoEncontradoException("Usuario con id " + usuarioId + " no encontrado");
        return repositoryPort.obtenerPermisosPorUsuarioId(usuarioId);
    }
}
