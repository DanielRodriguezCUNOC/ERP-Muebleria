package com.erp.muebleria.modules.administracion.infrastructure.adapters;

import com.erp.muebleria.modules.administracion.domain.entities.ConfiguracionSistema;
import com.erp.muebleria.modules.reportes.domain.entities.MetodoValoracion;
import com.erp.muebleria.modules.administracion.domain.ports.ConfiguracionSistemaRepositoryPort;
import com.erp.muebleria.modules.administracion.infrastructure.persistence.entities.ConfiguracionSistemaJpaEntity;
import com.erp.muebleria.modules.administracion.infrastructure.persistence.repositories.SpringDataConfiguracionRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

/**
 * Adaptador que implementa el puerto de repositorio para la entidad ConfiguracionSistema.
 * Este adaptador utiliza Spring Data JPA para interactuar con la base de datos.
 * Traduce los objetos del dominio a entidades JPA y viceversa.
 */
@Component
@AllArgsConstructor
public class ConfiguracionRepositoryAdapter implements ConfiguracionSistemaRepositoryPort {

    private final SpringDataConfiguracionRepository repository;

    @Override
    public Optional<ConfiguracionSistema> obtenerConfiguracion() {
        return repository.findById(1L).map(this::toDomain);
    }

    @Override
    public ConfiguracionSistema guardar(ConfiguracionSistema config) {
        ConfiguracionSistemaJpaEntity entity = toEntity(config);
        //* Aseguramos que siempre se guarde con ID 1
        entity.setId(1L);
        ConfiguracionSistemaJpaEntity savedEntity = repository.save(entity);
        return toDomain(savedEntity);
    }

    private ConfiguracionSistema toDomain(ConfiguracionSistemaJpaEntity entity) {
        return new ConfiguracionSistema(
                entity.getId(),
                entity.getTasaIva(),
                MetodoValoracion.valueOf(entity.getMetodoValoracion()),
                entity.getResolucionFacturas(),
                entity.getSerieFacturas(),
                entity.getCorrelativoSiguiente()
        );
    }

    private ConfiguracionSistemaJpaEntity toEntity(ConfiguracionSistema domain) {
        ConfiguracionSistemaJpaEntity entity = new ConfiguracionSistemaJpaEntity();
        entity.setId(domain.getId());
        entity.setTasaIva(domain.getTasaIva());
        entity.setMetodoValoracion(domain.getMetodoValoracion().name());
        entity.setResolucionFacturas(domain.getResolucionFacturas());
        entity.setSerieFacturas(domain.getSerieFacturas());
        entity.setCorrelativoSiguiente(domain.getCorrelativoSiguiente());
        return entity;
    }
}
