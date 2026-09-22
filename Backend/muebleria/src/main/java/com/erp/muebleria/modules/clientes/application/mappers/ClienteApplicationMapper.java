package com.erp.muebleria.modules.clientes.application.mappers;

import com.erp.muebleria.modules.clientes.application.dto.ClienteResponseDTO;
import com.erp.muebleria.modules.clientes.domain.entities.Cliente;
import com.erp.muebleria.modules.clientes.infrastructure.persistence.entities.ClienteJpaEntity;
import org.mapstruct.MappingTarget;

public interface ClienteApplicationMapper {

    ClienteJpaEntity toEntity(Cliente domain);

    Cliente toDomain(ClienteJpaEntity entity);

    void updateEntityFromDomain(Cliente domain, @MappingTarget ClienteJpaEntity entity);

    default ClienteResponseDTO toResponseDto(Cliente domain, String mensaje) {
        return new ClienteResponseDTO(
                domain.getId(),
                domain.getNombre(),
                domain.getNit(),
                domain.getDireccion(),
                domain.getTelefono(),
                domain.getActivo(),
                mensaje
        );
    }
}
