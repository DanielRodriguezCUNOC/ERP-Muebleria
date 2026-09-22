package com.erp.muebleria.modules.inventario.infrastructure.persistence.entities;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@Entity
@Table(name = "producto")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductoJpaEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nombre", nullable = false, length = 150)
    private String nombre;

    @Column(name = "categoria", nullable = false, length = 100)
    private String categoria;

    @Column(name = "precio", nullable = false, precision = 12, scale = 2)
    private BigDecimal precio;

    @Column(name = "activo", nullable = false)
    @Builder.Default
    private Boolean activo = true;

    @Column(name = "sku", nullable = false, unique = true, length = 60)
    private String sku;

    @Column(name = "existencia_minima", nullable = false)
    @Builder.Default
    private Integer existenciaMinima = 0;

    @OneToOne(mappedBy = "producto", cascade = CascadeType.ALL, fetch = FetchType.LAZY, optional = false)
    private InventarioJpaEntity inventario;
}