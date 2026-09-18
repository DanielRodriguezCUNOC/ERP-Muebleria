package com.erp.muebleria.modules.clientes.infrastructure.persistence;

import com.erp.muebleria.modules.clientes.domain.entities.Cliente;
import com.erp.muebleria.modules.clientes.domain.ports.ClienteRepositoryPort;
import com.erp.muebleria.modules.clientes.infrastructure.persistence.entities.ClienteJpaEntity;
import com.erp.muebleria.modules.clientes.infrastructure.persistence.mappers.ClienteMapper;
import com.erp.muebleria.modules.clientes.infrastructure.persistence.repositories.SpringDataClienteRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@AllArgsConstructor
public class PostgresClienteRepository implements ClienteRepositoryPort {

    private final SpringDataClienteRepository springDataClienteRepository;

    @Override
    public Optional<Cliente> buscarPorId(Long id) {
        return springDataClienteRepository.findById(id)
                .map(ClienteMapper::toDomain);
    }

    @Override
    public Cliente guardarClienteModificado(Cliente cliente) {
        ClienteJpaEntity entity = ClienteMapper.toEntity(cliente);
        ClienteJpaEntity savedEntity = springDataClienteRepository.save(entity);
        return ClienteMapper.toDomain(savedEntity);
    }

    @Override
    public List<Cliente> obtenerTodosLosClientes() {
        return springDataClienteRepository.findAll().stream()
                .map(ClienteMapper::toDomain)
                .toList();
    }
}