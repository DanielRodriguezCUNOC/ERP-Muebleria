package com.erp.muebleria.modules.clientes.domain.ports;

import com.erp.muebleria.modules.clientes.domain.entities.Cliente;

import java.util.List;
import java.util.Optional;

public interface ClienteRepositoryPort {
    Optional<Cliente> buscarPorId(Long id);
    Cliente guardarClienteModificado(Cliente cliente);
    List<Cliente> obtenerTodosLosClientes();

}
