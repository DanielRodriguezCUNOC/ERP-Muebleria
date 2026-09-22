package com.erp.muebleria.modules.common.infrastructure.web;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class ErrorResponseDTO {

    private int status;
    private String error;
    private String mensaje;
    private LocalDateTime timestamp;
}
