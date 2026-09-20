package com.erp.muebleria.modules.ventas.infrastructure.adapters;

import com.erp.muebleria.modules.ventas.domain.entities.Factura;
import com.erp.muebleria.modules.ventas.domain.entities.Venta;
import com.erp.muebleria.modules.ventas.domain.ports.FacturaPdfPort;
import org.springframework.stereotype.Component;

import java.io.ByteArrayOutputStream;
import java.nio.charset.StandardCharsets;

@Component
public class FacturaPdfAdapter implements FacturaPdfPort {
    @Override
    public byte[] generarPdf(Venta venta, Factura factura) {
        ByteArrayOutputStream out = new ByteArrayOutputStream();

        String contenido = String.format(
                "=== FACTURA: %s ===\nNIT: %s\nCliente: %s\nFecha: %s\nSubtotal: Q%.2f\nIVA: Q%.2f\nTotal: Q%.2f\n",
                factura.getNumeroFactura(),
                factura.getClienteNit(),
                factura.getClienteNombre(),
                factura.getFechaEmision(),
                venta.getSubTotal(),
                venta.getIva(),
                venta.getTotal()
        );
        out.writeBytes(contenido.getBytes(StandardCharsets.UTF_8));
        return out.toByteArray();
    }
}
