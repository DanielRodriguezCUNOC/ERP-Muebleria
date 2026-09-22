package com.erp.muebleria.modules.compras.infrastructure.persistence.entities;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "lote")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class LoteJpaEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "detalle_compra_id", nullable = false)
    private Long detalleCompraId;

    @Column(name = "producto_id", nullable = false)
    private Long productoId;

    @Column(name = "creado_en", nullable = false, updatable = false)
    private LocalDateTime creadoEn;

    @Column(name = "costo_unitario", nullable = false, precision = 12, scale = 2)
    private BigDecimal costoUnitario;

    @Column(name = "cantidad_disponible", nullable = false)
    private Integer cantidadDisponible;

    @Column(name = "cantidad_inicial", nullable = false)
    private Integer cantidadInicial;
}
