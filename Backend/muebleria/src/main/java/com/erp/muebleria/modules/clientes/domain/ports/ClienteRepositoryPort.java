package com.erp.muebleria.modules.clientes.domain.ports;

import com.erp.muebleria.modules.clientes.domain.entities.Cliente;

import java.util.List;
import java.util.Optional;

public interface ClienteRepositoryPort {
    Cliente guardar(Cliente cliente);
    Optional<Cliente> buscarPorNit(String nit);
    Optional<Cliente> buscarPorId(Long id);
    boolean existePorNit(String nit);
    Cliente guardarClienteModificado(Cliente cliente);
    List<Cliente> obtenerTodosLosClientes();


}
