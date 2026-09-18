package com.erp.muebleria.modules.compras.application.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RegistrarCompraRequestDTO {

    private Long proveedorId;
    private Long empleadoId;
    private List<DetalleCompraRequestDTO> detalles;
}
