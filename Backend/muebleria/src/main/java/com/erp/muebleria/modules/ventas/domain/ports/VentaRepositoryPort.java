package com.erp.muebleria.modules.ventas.domain.ports;

import com.erp.muebleria.modules.ventas.domain.entities.Factura;
import com.erp.muebleria.modules.ventas.domain.entities.Venta;

import java.util.Optional;

public interface VentaRepositoryPort {
    Venta guardarVentaConFactura(Venta venta, Factura factura);
    Optional<Venta> buscarPorId(Long id);
    Optional<Factura> buscarFacturaPorVentaId(Long ventaId);
}