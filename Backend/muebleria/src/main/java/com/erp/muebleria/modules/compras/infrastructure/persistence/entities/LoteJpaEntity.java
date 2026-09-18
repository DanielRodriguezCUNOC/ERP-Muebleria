package com.erp.muebleria.modules.compras.infrastructure.persistence.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "lote")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class LoteJpaEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "detalle_compra_id", nullable = false)
    private Long detalleCompraId;

    @Column(name = "product_id", nullable = false)
    private Long productId;

    @Column(name = "creado_en", nullable = false, updatable = false)
    private LocalDateTime creadoEn;

    @Column(name = "costo_unitario", nullable = false, precision = 10, scale = 2)
    private BigDecimal costoUnitario;

    @Column(name = "cantidad_disponible", nullable = false)
    private Integer cantidadDisponible;

    @Column(name = "cantidad_inicial", nullable = false)
    private Integer cantidadInicial;
}
