package com.erp.muebleria.modules.compras.infrastructure.persistence.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Entity
@Table(name = "detalle_compra")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class DetalleCompraJpaEntity {

    @EmbeddedId
    private DetalleCompraIdJpaEntity id;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("compraId")
    @JoinColumn(name = "compra_id", nullable = false)
    private CompraJpaEntity compra;

    @Column(name = "proveedor_id", nullable = false)
    private Long proveedorId;

    @Column(name = "cantidad", nullable = false)
    private Integer cantidad;

    @Column(name = "precio_unitario", precision = 12, scale = 2)
    private BigDecimal precioUnitario;

    @Column(name = "costo_total", precision = 14, scale = 2)
    private BigDecimal costoTotal;
}
