package com.erp.muebleria.modules.administracion.domain.ports;

import com.erp.muebleria.modules.administracion.domain.models.TopCliente;

import java.util.List;

/**
 * Puerto de repositorio para obtener reportes gerenciales.
 * Define los métodos para los reportes que el administrador puede solicitar.
 */
public interface ReportesGerencialesRepositoryPort {
    List<TopCliente> obtenerTopClientesPorMonto(int limite);
}
