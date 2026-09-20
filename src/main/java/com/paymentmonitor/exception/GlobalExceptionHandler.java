package com.paymentmonitor.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(TransactionNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public Map<String, Object> handleTransactionNotFound(
            TransactionNotFoundException exception) {

        return Map.of(
                "status", 404,
                "message", exception.getMessage()
        );
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Map<String, Object> handleValidationErrors(
            MethodArgumentNotValidException exception) {

        Map<String, String> fieldErrors = new HashMap<>();

        exception.getBindingResult().getFieldErrors().forEach(error ->
                fieldErrors.put(error.getField(), error.getDefaultMessage())
        );

        return Map.of(
                "status", 400,
                "message", "Validation failed",
                "errors", fieldErrors
        );
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Map<String, Object> handleTypeMismatch(
            MethodArgumentTypeMismatchException exception) {

        String message = String.format(
                "Invalid value '%s' for parameter '%s'",
                exception.getValue(),
                exception.getName()
        );

        return Map.of(
                "status", 400,
                "message", message
        );
    }

    @ExceptionHandler(IllegalArgumentException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Map<String, Object> handleIllegalArgument(IllegalArgumentException exception) {

        return Map.of(
                "status", 400,
                "message", exception.getMessage()
        );
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public Map<String, Object> handleUnexpectedError(Exception exception) {

        logger.error("Unexpected error occurred", exception);

        return Map.of(
                "status", 500,
                "message", "An unexpected error occurred"
        );
    }
}