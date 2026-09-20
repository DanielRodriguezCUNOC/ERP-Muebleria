package com.erp.muebleria.modules.ventas.domain.ports;

import com.erp.muebleria.modules.ventas.domain.entities.Factura;
import com.erp.muebleria.modules.ventas.domain.entities.Venta;

public interface FacturaPdfPort {
    byte[] generarPdf(Venta venta, Factura factura);
}