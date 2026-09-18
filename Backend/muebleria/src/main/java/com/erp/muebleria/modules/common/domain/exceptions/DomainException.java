package com.erp.muebleria.modules.common.domain.exceptions;

public abstract class DomainException extends RuntimeException{
    public DomainException(String message) {
        super(message);
    }
}
