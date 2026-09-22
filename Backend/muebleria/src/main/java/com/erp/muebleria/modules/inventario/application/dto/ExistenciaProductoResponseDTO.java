package com.erp.muebleria.modules.inventario.application.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ExistenciaProductoResponseDTO {
    private Long productoId;
    private String sku;
    private String nombre;
    private Integer existenciaTotal;
    private Integer existenciaMinima;
    private List<LoteExistenciaDTO> lotesDisponibles = new ArrayList<>();
}