package com.raghav.desibusiness.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(PhoneNotVerifiedException.class)
    public ResponseEntity<String> handlePhoneNotVerified(
            PhoneNotVerifiedException ex) {

        return ResponseEntity
                .status(HttpStatus.FORBIDDEN)
                .body(ex.getMessage());
    }
}