package com.vishal.picontrolcentre.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(GlancesUnavailableException.class)
    public ResponseEntity<String> handleGlancesUnavailableException(GlancesUnavailableException glancesUnavailableException){
        return new ResponseEntity<>(
                glancesUnavailableException.getMessage(),
                HttpStatus.SERVICE_UNAVAILABLE
        );
    }

}
