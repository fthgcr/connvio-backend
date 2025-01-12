package com.connvio.common.exception;

import lombok.Getter;

@Getter
public class ConnvioException extends RuntimeException {
    private final ErrorCode errorCode;

    public ConnvioException(ErrorCode errorCode) {
        super(errorCode.getTurkishMessage());
        this.errorCode = errorCode;
    }
} 