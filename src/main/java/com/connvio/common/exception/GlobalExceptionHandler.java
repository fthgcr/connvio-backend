package com.connvio.common.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ConnvioException.class)
    public ResponseEntity<ErrorResponse> handleConnvioException(ConnvioException ex) {
        ErrorResponse response = new ErrorResponse(
            ex.getErrorCode().getCode(),
            ex.getErrorCode().getTurkishMessage(),
            ex.getErrorCode().getEnglishMessage()
        );
        return ResponseEntity.badRequest().body(response);
    }

    // Diğer exception handler'lar buraya eklenebilir
} 