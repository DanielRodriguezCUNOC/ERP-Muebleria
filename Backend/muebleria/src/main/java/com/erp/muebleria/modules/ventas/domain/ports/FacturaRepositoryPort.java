package com.erp.muebleria.modules.ventas.domain.ports;

import com.erp.muebleria.modules.ventas.domain.entities.Factura;

import java.util.Optional;

public interface FacturaRepositoryPort {
    Optional<Factura> buscarPorId(Long id);
    Optional<Factura> buscarPorNumero(String numeroFactura);
    Factura guardarFactura(Factura factura);
}