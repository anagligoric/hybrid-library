package com.hybridlibrary.exceptions;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.validation.ConstraintViolationException;
import java.util.Date;

@ControllerAdvice
public class CustomGlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<Object> handleNotFoundException(NotFoundException ex, WebRequest request) {
        String errorMessageDescription = ex.getLocalizedMessage();
        if (errorMessageDescription == null) {
            errorMessageDescription = ex.toString();
        }
        ErrorMessage errorMessage = new ErrorMessage(errorMessageDescription, new Date(), HttpStatus.NOT_FOUND.value());
        return new ResponseEntity<>(errorMessage, new HttpHeaders(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<Object> constraintViolationException(ConstraintViolationException ex) {
        String errorMessageDescription = ex.getLocalizedMessage();

        if (errorMessageDescription == null) {
            errorMessageDescription = ex.toString();
        }
        ErrorMessage errorMessage = new ErrorMessage(errorMessageDescription, new Date(), HttpStatus.CONFLICT.value());
        return new ResponseEntity<>(errorMessage, new HttpHeaders(), HttpStatus.CONFLICT);
    }

    @ExceptionHandler(HttpServerErrorException.InternalServerError.class)
    public ResponseEntity<Object> serverError(HttpServerErrorException.InternalServerError ex) {
        String errorMessageDescription = ex.getLocalizedMessage();
        ErrorMessage errorMessage = new ErrorMessage(errorMessageDescription, new Date(), HttpStatus.INTERNAL_SERVER_ERROR.value());
        return new ResponseEntity<>(errorMessage, new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR);
    }


}
