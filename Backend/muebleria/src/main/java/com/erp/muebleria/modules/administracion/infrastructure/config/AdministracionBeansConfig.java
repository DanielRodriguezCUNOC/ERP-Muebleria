package com.erp.muebleria.modules.administracion.infrastructure.config;

import com.erp.muebleria.modules.administracion.application.useCases.*;
import com.erp.muebleria.modules.administracion.domain.ports.ConfiguracionSistemaRepositoryPort;
import com.erp.muebleria.modules.reportes.domain.ports.ReportesGerencialesRepositoryPort;
import com.erp.muebleria.modules.reportes.application.useCases.*;
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
}
