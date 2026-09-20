package com.erp.muebleria.modules.inventario.infrastructure.adapters;

import com.erp.muebleria.modules.inventario.domain.entities.ProductoInventario;
import com.erp.muebleria.modules.inventario.domain.ports.InventarioRepositoryPort;
import com.erp.muebleria.modules.inventario.infrastructure.persistence.mappers.InventarioMapper;
import com.erp.muebleria.modules.inventario.infrastructure.persistence.repositories.SpringDataInventarioRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class InventarioRepositoryAdapter implements InventarioRepositoryPort {

    private final SpringDataInventarioRepository repository;
    private final InventarioMapper mapper;

    @Override
    public Page<ProductoInventario> buscarExistencias(String busqueda, Boolean soloBajoStock, Pageable pageable) {
        Boolean filtroBajoStock = Boolean.TRUE.equals(soloBajoStock);
        String filtroBusqueda = (busqueda != null && !busqueda.isBlank()) ? busqueda.trim() : null;

        return repository.buscarExistencias(filtroBusqueda, filtroBajoStock, pageable)
                .map(mapper::toDomain);
    }
}