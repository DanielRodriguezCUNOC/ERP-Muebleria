package com.erp.muebleria.modules.clientes.application.useCases;

import com.erp.muebleria.modules.clientes.domain.entities.Cliente;
import com.erp.muebleria.modules.clientes.domain.ports.ClienteRepositoryPort;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class CambiarEstadoClienteUseCase {

    private final ClienteRepositoryPort clienteRepositoryPort;

    public void ejecutar(Long clienteId, Boolean activo) {
        Cliente cliente = clienteRepositoryPort.buscarPorId(clienteId).orElseThrow(() -> new RuntimeException("Cliente no encontrado"));

        if (activo) {
            cliente.activar();
        } else {
            cliente.desactivar();
        }

        clienteRepositoryPort.guardarClienteModificado(cliente);
    }
}
