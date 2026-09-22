package com.erp.muebleria.modules.clientes.infrastructure.adapters;

import com.erp.muebleria.modules.clientes.domain.entities.Cliente;
import com.erp.muebleria.modules.clientes.domain.ports.ClienteRepositoryPort;
import com.erp.muebleria.modules.clientes.infrastructure.persistence.entities.ClienteJpaEntity;
import com.erp.muebleria.modules.clientes.infrastructure.persistence.mappers.ClienteMapper;
import com.erp.muebleria.modules.clientes.infrastructure.persistence.repositories.SpringDataClienteRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@AllArgsConstructor
public class ClienteRepositoryAdapter implements ClienteRepositoryPort {

    private final SpringDataClienteRepository repository;
    private final ClienteMapper mapper;

    @Override
    public Cliente guardar(Cliente cliente) {
        ClienteJpaEntity entity = mapper.toEntity(cliente);
        ClienteJpaEntity saved = repository.save(entity);
        return mapper.toDomain(saved);
    }

    @Override
    public Optional<Cliente> buscarPorId(Long id) {
        return repository.findById(id).map(mapper::toDomain);
    }

    @Override
    public Optional<Cliente> buscarPorNit(String nit) {
        return repository.findByNit(nit).map(mapper::toDomain);
    }

    @Override
    public boolean existePorNit(String nit) {
        return repository.existsByNit(nit);
    }

    //* Ya utilizados en el modulo de usuarios
    @Override
    public Cliente guardarClienteModificado(Cliente cliente) {
        return null;
    }

    @Override
    public org.springframework.data.domain.Page<Cliente> obtenerTodosLosClientes(org.springframework.data.domain.Pageable pageable) {
        return repository.findAll(pageable).map(mapper::toDomain);
    }
}