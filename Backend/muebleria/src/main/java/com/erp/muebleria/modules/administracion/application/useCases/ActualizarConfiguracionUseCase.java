package com.erp.muebleria.modules.administracion.application.useCases;

import com.erp.muebleria.modules.administracion.application.dto.ActualizarConfiguracionDTO;
import com.erp.muebleria.modules.administracion.domain.entities.ConfiguracionSistema;
import com.erp.muebleria.modules.administracion.domain.ports.ConfiguracionSistemaRepositoryPort;
import com.erp.muebleria.modules.common.domain.exceptions.RecursoNoEncontradoException;
import com.erp.muebleria.modules.common.domain.exceptions.ReglaNegocioException;
import com.erp.muebleria.modules.reportes.domain.entities.MetodoValoracion;
import lombok.AllArgsConstructor;

/**
 * Caso de uso para actualizar la configuración del sistema.
 * Actualiza solo los campos que se proporcionan en el DTO, dejando los demás sin cambios (evitamos cagarla).
 */
@AllArgsConstructor
public class ActualizarConfiguracionUseCase {

    private final ConfiguracionSistemaRepositoryPort repositoryPort;

    public ConfiguracionSistema ejecutar (ActualizarConfiguracionDTO dto) {

        //* Obtener la configuración actual
        ConfiguracionSistema configActual = repositoryPort.obtenerConfiguracion().
                orElseThrow(
                        () -> new RecursoNoEncontradoException("No se encontró el registro de configuración del sistema")
                );
        //* Actualizar los valores si vienen en la petición
        actualizarValores(configActual, dto);
        //* Delegar persistencia al puerto de infraestructura
        return repositoryPort.guardar(configActual);
    }


    private void actualizarValores(ConfiguracionSistema config, ActualizarConfiguracionDTO dto) {
        if (dto.getTasaIva() != null) {
            config.setTasaIva(dto.getTasaIva());
        }
        if (dto.getMetodoValoracion() != null) {
            try {
                config.setMetodoValoracion(
                        MetodoValoracion.valueOf(dto.getMetodoValoracion())
                );
            } catch (IllegalArgumentException e) {
                throw new ReglaNegocioException("El método de valoración proporcionado no es válido:" + dto.getMetodoValoracion());
            }
        }
        if (dto.getResolucionFactura() != null) {
            config.setResolucionFacturas(dto.getResolucionFactura());
        }
        if (dto.getSerieFacturas() != null) {
            config.setSerieFacturas(dto.getSerieFacturas());
        }
        if (dto.getCorrelativoSiguiente() != null) {
            config.setCorrelativoSiguiente(dto.getCorrelativoSiguiente());
        }
    }
}
