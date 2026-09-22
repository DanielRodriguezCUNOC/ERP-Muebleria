package com.erp.muebleria.modules.administracion.infrastructure.config;

import com.erp.muebleria.modules.administracion.application.useCases.*;
import com.erp.muebleria.modules.administracion.domain.ports.ConfiguracionSistemaRepositoryPort;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AdministracionBeansConfig {

    @Bean
    public ActualizarConfiguracionUseCase actualizarConfiguracionUseCase(
            ConfiguracionSistemaRepositoryPort configuracionPort
    ) {
        return new ActualizarConfiguracionUseCase(configuracionPort);
    }

    @Bean
    public ObtenerConfiguracionUseCase obtenerConfiguracionUseCase(
            ConfiguracionSistemaRepositoryPort configuracionPort
    ) {
        return new ObtenerConfiguracionUseCase(configuracionPort);
    }
}
