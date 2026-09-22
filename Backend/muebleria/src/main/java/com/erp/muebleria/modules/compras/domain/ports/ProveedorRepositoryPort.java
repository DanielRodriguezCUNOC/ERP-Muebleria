package com.erp.muebleria.modules.compras.domain.ports;

import com.erp.muebleria.modules.compras.application.dto.ProveedorResponseDTO;
import com.erp.muebleria.modules.compras.domain.entities.Proveedor;

import java.util.List;
import java.util.Optional;

public interface ProveedorRepositoryPort {

    Proveedor guardar(Proveedor proveedor);
    Optional<Proveedor> buscarPorId(Long id);
    List<ProveedorResponseDTO> buscarTodos(String nombre);
}
