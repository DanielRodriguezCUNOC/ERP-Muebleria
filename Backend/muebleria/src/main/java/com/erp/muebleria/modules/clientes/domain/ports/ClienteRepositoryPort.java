package com.erp.muebleria.modules.clientes.domain.ports;

import com.erp.muebleria.modules.clientes.domain.entities.Cliente;

import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ClienteRepositoryPort {
    Cliente guardar(Cliente cliente);

    Optional<Cliente> buscarPorNit(String nit);

    Optional<Cliente> buscarPorId(Long id);

    boolean existePorNit(String nit);

    Cliente guardarClienteModificado(Cliente cliente);

    Page<Cliente> obtenerTodosLosClientes(Pageable pageable);

}
