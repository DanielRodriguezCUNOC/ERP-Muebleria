package com.erp.muebleria.modules.compras.domain.ports;

import com.erp.muebleria.modules.compras.domain.entities.Proveedor;

import java.util.Optional;

public interface ProveedorRepositoryPort {

    Proveedor guardar(Proveedor proveedor);
    Optional<Proveedor> buscarPorId(Long id);
}
