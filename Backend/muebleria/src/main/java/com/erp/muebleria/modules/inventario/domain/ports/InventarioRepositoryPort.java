package com.erp.muebleria.modules.inventario.domain.ports;

import com.erp.muebleria.modules.inventario.domain.entities.ProductoInventario;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface InventarioRepositoryPort {
    Page<ProductoInventario> buscarExistencias(String busqueda, Boolean soloBajoStock, Pageable pageable);
}
