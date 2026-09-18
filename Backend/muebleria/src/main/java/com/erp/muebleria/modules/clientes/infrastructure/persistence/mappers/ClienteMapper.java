package com.erp.muebleria.modules.clientes.infrastructure.persistence.mappers;

import com.erp.muebleria.modules.clientes.domain.entities.Cliente;
import com.erp.muebleria.modules.clientes.infrastructure.persistence.entities.ClienteJpaEntity;

public class ClienteMapper {

    public static Cliente toDomain(ClienteJpaEntity entity) {
        if (entity == null) return null;

        return new Cliente(
                entity.getId(),
                entity.getNombre(),
                entity.getNit(),
                entity.getDireccion(),
                entity.getTelefono(),
                entity.getActivo()
        );
    }

    public static ClienteJpaEntity toEntity(Cliente domain) {
        if (domain == null) return null;

        ClienteJpaEntity entity = new ClienteJpaEntity();
        entity.setId(domain.getId());
        entity.setNombre(domain.getNombre());
        entity.setNit(domain.getNit());
        entity.setDireccion(domain.getDireccion());
        entity.setTelefono(domain.getTelefono());
        entity.setActivo(domain.getActivo());

        return entity;
    }
}