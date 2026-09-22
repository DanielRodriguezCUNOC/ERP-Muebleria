package com.erp.muebleria.modules.usuarios.application.useCases.usuarios;

import com.erp.muebleria.modules.usuarios.application.dto.UsuarioResponseDTO;
import com.erp.muebleria.modules.usuarios.domain.ports.UsuarioRepositoryPort;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class ObtenerUsuarioUseCase {

    private final UsuarioRepositoryPort usuarioRepositoryPort;

    public List<UsuarioResponseDTO> ejecutar () {
        return usuarioRepositoryPort.obtenerTodosLosUsuarios()
                .stream()
                .map(UsuarioResponseDTO::desdeDominio)
                .toList();
    }
}
