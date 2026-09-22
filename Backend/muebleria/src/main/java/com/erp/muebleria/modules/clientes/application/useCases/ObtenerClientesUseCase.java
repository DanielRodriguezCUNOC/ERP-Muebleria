package com.erp.muebleria.modules.clientes.application.useCases;

import com.erp.muebleria.modules.clientes.application.dto.ClienteResponseDTO;
import com.erp.muebleria.modules.clientes.domain.ports.ClienteRepositoryPort;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class ObtenerClientesUseCase {

    private final ClienteRepositoryPort repositoryPort;

    public Page<ClienteResponseDTO> ejecutar (Pageable pageable){
        return repositoryPort.obtenerTodosLosClientes(pageable)
                .map(ClienteResponseDTO::desdeDominio);
    }
}
