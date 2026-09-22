package com.erp.muebleria.modules.administracion.application.useCases;

import com.erp.muebleria.modules.administracion.application.dto.ConfiguracionSistemaResponseDTO;
import com.erp.muebleria.modules.administracion.domain.entities.ConfiguracionSistema;
import com.erp.muebleria.modules.administracion.domain.ports.ConfiguracionSistemaRepositoryPort;
import com.erp.muebleria.modules.common.domain.exceptions.RecursoNoEncontradoException;

public class ObtenerConfiguracionUseCase {

    private final ConfiguracionSistemaRepositoryPort repositoryPort;

    public ObtenerConfiguracionUseCase(ConfiguracionSistemaRepositoryPort repositoryPort) {
        this.repositoryPort = repositoryPort;
    }

    public ConfiguracionSistemaResponseDTO ejecutar() {
        ConfiguracionSistema config = repositoryPort.obtenerConfiguracion()
                .orElseThrow(() -> new RecursoNoEncontradoException("No se encontró la configuración del sistema"));

        return ConfiguracionSistemaResponseDTO.builder()
                .id(config.getId())
                .tasaIva(config.getTasaIva())
                .metodoValoracion(config.getMetodoValoracion())
                .resolucionFacturas(config.getResolucionFacturas())
                .serieFacturas(config.getSerieFacturas())
                .correlativoSiguiente(config.getCorrelativoSiguiente())
                .build();
    }
}