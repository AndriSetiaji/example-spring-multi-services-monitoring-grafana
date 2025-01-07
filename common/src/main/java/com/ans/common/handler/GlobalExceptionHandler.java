package com.ans.common.handler;

import io.opentelemetry.api.trace.Span;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleException(Exception e) {
        // Secara otomatis menangkap span saat exception terjadi
        Span currentSpan = Span.current();
        currentSpan.recordException(e); // Catat exception
        currentSpan.setStatus(io.opentelemetry.api.trace.StatusCode.ERROR); // Tandai sebagai error

        // Berikan response ke client
        return ResponseEntity.internalServerError().body("An error occurred: " + e.getMessage());
    }
}
