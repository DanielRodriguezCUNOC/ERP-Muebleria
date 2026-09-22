package com.erp.muebleria.modules.ventas.infrastructure.persistence.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "venta")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class VentaJpaEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "empleado_id", nullable = false)
    private Long empleadoId;

    @Column(name = "cliente_id", nullable = false)
    private Long clienteId;

    @Column(name = "fecha_venta", nullable = false)
    private LocalDateTime fechaVenta = LocalDateTime.now();

    @Column(name = "sub_total", nullable = false, precision = 14, scale = 2)
    private BigDecimal subTotal;

    @Column(name = "iva", nullable = false, precision = 14, scale = 2)
    private BigDecimal iva;

    @Column(name = "total", nullable = false, precision = 14, scale = 2)
    private BigDecimal total;

    @OneToMany(mappedBy = "venta", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<DetalleVentaJpaEntity> detalles = new ArrayList<>();
}