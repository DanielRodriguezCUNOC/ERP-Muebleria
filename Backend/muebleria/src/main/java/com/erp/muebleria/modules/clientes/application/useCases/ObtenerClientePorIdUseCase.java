package com.erp.muebleria.modules.clientes.application.useCases;

import com.erp.muebleria.modules.clientes.application.dto.ClienteResponseDTO;
import com.erp.muebleria.modules.clientes.domain.ports.ClienteRepositoryPort;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class ObtenerClientePorIdUseCase {

    private final ClienteRepositoryPort repositoryPort;

    public ClienteResponseDTO ejecutar(Long id) {
        return repositoryPort.buscarPorId(id)
                .map(ClienteResponseDTO::desdeDominio)
                .orElseThrow(() -> new RuntimeException("Cliente no encontrado con id: " + id));
    }
}
