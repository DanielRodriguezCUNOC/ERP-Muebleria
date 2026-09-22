package com.erp.muebleria.modules.ventas.infrastructure.persistence.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * Clase que representa la clave primaria compuesta de la entidad DetalleVenta.
 * Esta clase es utilizada para mapear la relación entre Venta y Producto en la base de datos.
 */
@Embeddable
@Data
@AllArgsConstructor
@NoArgsConstructor
public class DetalleVentaPK implements Serializable {

    @Column(name = "venta_id")
    private Long ventaId;

    @Column(name = "producto_id")
    private Long productoId;
}