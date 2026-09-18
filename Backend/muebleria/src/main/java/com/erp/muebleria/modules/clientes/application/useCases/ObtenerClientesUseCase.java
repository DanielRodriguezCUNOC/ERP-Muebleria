package com.erp.muebleria.modules.clientes.application.useCases;

import com.erp.muebleria.modules.clientes.application.dto.ClienteResponseDTO;
import com.erp.muebleria.modules.clientes.domain.ports.ClienteRepositoryPort;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class ObtenerClientesUseCase {

    private final ClienteRepositoryPort repositoryPort;

    public List<ClienteResponseDTO> ejecutar (){
        return repositoryPort.obtenerTodosLosClientes()
                .stream()
                .map(ClienteResponseDTO::desdeDominio)
                .toList();
    }
}
