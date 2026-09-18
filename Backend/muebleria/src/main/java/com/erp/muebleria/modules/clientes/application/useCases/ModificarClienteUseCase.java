package com.erp.muebleria.modules.clientes.application.useCases;

import com.erp.muebleria.modules.clientes.application.dto.ModificarClienteDTO;
import com.erp.muebleria.modules.clientes.domain.entities.Cliente;
import com.erp.muebleria.modules.clientes.domain.ports.ClienteRepositoryPort;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class ModificarClienteUseCase {
    private final ClienteRepositoryPort clienteRepository;

    public void ejecutar (Long id, ModificarClienteDTO dto) {
        Cliente cliente = clienteRepository.buscarPorId(id).orElseThrow(
                () -> new RuntimeException("El cliente con id " + id + " no existe")
        );
        cliente.actualizarInformacion(dto.getNombre(), dto.getNit(), dto.getDireccion(), dto.getTelefono());
        clienteRepository.guardarClienteModificado(cliente);
    }

}
