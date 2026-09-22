package com.erp.muebleria.modules.clientes.application.useCases;

import org.springframework.stereotype.Service;

import com.erp.muebleria.modules.clientes.application.dto.ClienteResponseDTO;
import com.erp.muebleria.modules.clientes.domain.ports.ClienteRepositoryPort;
import com.erp.muebleria.modules.common.domain.exceptions.RecursoNoEncontradoException;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class ObtenerClientePorNitUseCase {

  private final ClienteRepositoryPort repositoryPort;

  public ClienteResponseDTO ejecutar(String nit) {
    return repositoryPort.buscarPorNit(nit)
        .map(ClienteResponseDTO::desdeDominio)
        .orElseThrow(() -> new RecursoNoEncontradoException("Cliente no encontrado con NIT: " + nit));
  }
}
