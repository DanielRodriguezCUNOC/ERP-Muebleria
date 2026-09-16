package com.erp.muebleria.modules.administracion.domain.ports;

import com.erp.muebleria.modules.administracion.domain.entities.BitacoraOperacion;

import java.util.List;

public interface BitacoraOperacionRepositoryPort {

    BitacoraOperacion guardar (BitacoraOperacion bitacora);

    List<BitacoraOperacion> buscarPorEmpleado (Long empleadoId);
}
