package com.erp.muebleria.modules.administracion.infrastructure.config;

import com.erp.muebleria.modules.administracion.application.useCases.ActualizarConfiguracionUseCase;
import com.erp.muebleria.modules.administracion.application.useCases.GenerarReporteTopClientesUseCase;
import com.erp.muebleria.modules.administracion.domain.ports.ConfiguracionSistemaRepositoryPort;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AdministracionBeansConfig {

    @Bean
    public ActualizarConfiguracionUseCase actualizarConfiguracionUseCase(
            ConfiguracionSistemaRepositoryPort configuracionSistemaRepositoryPort
    ){
        return new ActualizarConfiguracionUseCase(configuracionSistemaRepositoryPort);
    }

    @Bean
    public GenerarReporteTopClientesUseCase generarReporteTopClientesUseCase(
            com.erp.muebleria.modules.administracion.domain.ports.ReportesGerencialesRepositoryPort reportesGerencialesRepositoryPort
    ){
        return new GenerarReporteTopClientesUseCase(reportesGerencialesRepositoryPort);
    }
}
