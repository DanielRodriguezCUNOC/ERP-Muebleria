package com.erp.muebleria.modules.administracion.domain.ports;

import com.erp.muebleria.modules.administracion.domain.entities.ConfiguracionSistema;

import java.util.Optional;

/**
 * Puerto de salida para la persistencia de la configuración del sistema.
 * Define los métodos que deben implementarse en la capa de infraestructura para interactuar con la base de datos.
 */
public interface ConfiguracionSistemaRepositoryPort {

    Optional<ConfiguracionSistema> obtenerConfiguracion();
    ConfiguracionSistema guardar(ConfiguracionSistema config);
}
