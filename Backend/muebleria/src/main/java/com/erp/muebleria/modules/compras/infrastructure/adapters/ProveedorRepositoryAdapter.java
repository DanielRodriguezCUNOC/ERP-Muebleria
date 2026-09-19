package com.erp.muebleria.modules.compras.infrastructure.adapters;

import com.erp.muebleria.modules.compras.domain.entities.Proveedor;
import com.erp.muebleria.modules.compras.domain.ports.ProveedorRepositoryPort;
import com.erp.muebleria.modules.compras.infrastructure.persistence.entities.ProveedorJpaEntity;
import com.erp.muebleria.modules.compras.infrastructure.persistence.mappers.ProveedorMapper;
import com.erp.muebleria.modules.compras.infrastructure.persistence.repositories.SpringDataProveedorRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@AllArgsConstructor
public class ProveedorRepositoryAdapter implements ProveedorRepositoryPort {

    private final SpringDataProveedorRepository repository;
    private final ProveedorMapper mapper;


    @Override
    public Proveedor guardar(Proveedor proveedor) {
        ProveedorJpaEntity entity = mapper.toEntity(proveedor);
        ProveedorJpaEntity savedEntity = repository.save(entity);
        return mapper.toDomain(savedEntity);
    }

    @Override
    public Optional<Proveedor> buscarPorId(Long id) {
        return repository.findById(id).map(mapper::toDomain);
    }
}
