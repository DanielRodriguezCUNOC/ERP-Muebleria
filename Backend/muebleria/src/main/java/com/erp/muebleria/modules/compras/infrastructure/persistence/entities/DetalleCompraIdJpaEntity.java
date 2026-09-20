package com.erp.muebleria.modules.compras.infrastructure.persistence.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.*;

import java.io.Serializable;

@Embeddable
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class DetalleCompraIdJpaEntity implements Serializable {

    @Column(name = "compra_id")
    private Long compraId;

    @Column(name = "producto_id")
    private Long productoId;
}
