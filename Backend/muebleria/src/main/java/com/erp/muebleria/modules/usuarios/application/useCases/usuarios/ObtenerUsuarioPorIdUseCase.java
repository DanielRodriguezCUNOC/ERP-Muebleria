package com.erp.muebleria.modules.usuarios.application.useCases.usuarios;

import com.erp.muebleria.modules.usuarios.application.dto.UsuarioResponseDTO;
import com.erp.muebleria.modules.usuarios.domain.ports.UsuarioRepositoryPort;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class ObtenerUsuarioPorIdUseCase {

    private final UsuarioRepositoryPort usuarioRepositoryPort;

    public UsuarioResponseDTO ejecutar(Long id) {
        return usuarioRepositoryPort.buscarPorId(id)
                .map(UsuarioResponseDTO::desdeDominio)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado con id: " + id));
    }
}
