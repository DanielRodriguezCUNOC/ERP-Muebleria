package com.erp.muebleria.modules.administracion.infrastructure.adapters;

import com.erp.muebleria.modules.administracion.domain.entities.BitacoraOperacion;
import com.erp.muebleria.modules.administracion.domain.ports.BitacoraOperacionRepositoryPort;
import com.erp.muebleria.modules.administracion.infrastructure.persistence.entities.BitacoraOperacionJpaEntity;
import com.erp.muebleria.modules.administracion.infrastructure.persistence.mappers.BitacoraMapper;
import com.erp.muebleria.modules.administracion.infrastructure.persistence.repositories.SpringDataBitacoraRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Adaptador que implementa el puerto de repositorio para la bitácora de operaciones.
 * Este adaptador se encarga de interactuar con la capa de persistencia para guardar y buscar registros de bitácora.
 */
@Component
@AllArgsConstructor
public class BitacoraRepositoryAdapter implements BitacoraOperacionRepositoryPort {

    private final SpringDataBitacoraRepository repository;
    private final BitacoraMapper mapper;

    /**
     * Guarda un registro de bitácora en la base de datos.
     */
    @Override
    public BitacoraOperacion guardar(BitacoraOperacion bitacora) {
        BitacoraOperacionJpaEntity entity = mapper.toEntity(bitacora);
        BitacoraOperacionJpaEntity savedEntity = repository.save(entity);
        return mapper.toDomain(savedEntity);
    }

    /**
     * Busca registros de bitácora asociados a un empleado específico.
     * Transforma los resultados de la base de datos a objetos de dominio antes de devolverlos.
     */
    @Override
    public List<BitacoraOperacion> buscarPorEmpleado(Long empleadoId) {
        return repository.findByEmpleadoIdOrderByFechaDesc(empleadoId)
                .stream()
                .map(mapper::toDomain)
                .toList();
    }
}
