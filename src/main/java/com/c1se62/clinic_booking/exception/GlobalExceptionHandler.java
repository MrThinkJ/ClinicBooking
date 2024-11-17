package com.c1se62.clinic_booking.exception;

import com.c1se62.clinic_booking.dto.response.ErrorDetails;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler {
    private ResponseEntity<ErrorDetails> createErrorResponse(Exception exception, WebRequest webRequest, HttpStatus status) {
        ErrorDetails errorDetails = new ErrorDetails(
                new Date(),
                exception.getMessage(),
                webRequest.getDescription(false)
        );
        return new ResponseEntity<>(errorDetails, status);
    }
    @ExceptionHandler(APIException.class)
    public ResponseEntity<ErrorDetails> handleAPIException(Exception e, WebRequest webRequest, HttpStatus status) {
        return createErrorResponse(e, webRequest, status);
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorDetails> handleResourceNotFoundException(ResourceNotFoundException e, WebRequest webRequest) {
        return createErrorResponse(e, webRequest, HttpStatus.NOT_FOUND);
    }

//    @ExceptionHandler(Exception.class)
//    public ResponseEntity<ErrorDetails> handleAllExceptions(Exception e, WebRequest webRequest) {
//        return createErrorResponse(e, webRequest, HttpStatus.INTERNAL_SERVER_ERROR);
//    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });

        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
    }
}
