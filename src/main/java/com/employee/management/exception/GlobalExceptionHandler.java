package com.employee.management.exception;

import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.List;

@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    private final MessageSource messageSource;

    public GlobalExceptionHandler(MessageSource messageSource) {
        this.messageSource = messageSource;
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(
            MethodArgumentNotValidException ex,
            HttpHeaders headers,
            HttpStatusCode status,
            WebRequest request) {
        return buildResponseEntity(
                getValidationErrors(ex.getBindingResult().getFieldErrors()),
                "Invalid request",
                "validation.failed",
                HttpStatus.BAD_REQUEST);
    }

    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(
            HttpMessageNotReadableException ex,
            HttpHeaders headers,
            HttpStatusCode status,
            WebRequest request) {
        return buildResponseEntity(null, "Invalid request body", "invalid.request.body", HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<Object> handleMethodArgumentTypeMismatch(MethodArgumentTypeMismatchException ex) {
        return buildResponseEntity(null, ex.getName() + " has invalid value", "invalid.parameter.value", HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<Object> handleNotFound(ResourceNotFoundException ex) {
        return buildResponseEntity(null, ex.getMessage(), "resource.not.found", HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(InvalidLeaveStateException.class)
    public ResponseEntity<Object> handleInvalidLeaveState(InvalidLeaveStateException ex) {
        return buildResponseEntity(null, ex.getMessage(), "invalid.leave.state", HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> handleAny(Exception ex) {
        return buildResponseEntity(null, "An unexpected error occurred", "internal.server.error", HttpStatus.INTERNAL_SERVER_ERROR);
    }

    private List<ApiSubError> getValidationErrors(List<FieldError> fieldErrors) {
        return fieldErrors.stream()
                .map(fe -> new ApiSubError(fe.getField(), fe.getDefaultMessage()))
                .toList();
    }

    private ResponseEntity<Object> buildResponseEntity(List<ApiSubError> subErrors,
                                                       String defaultMessage,
                                                       String errorMessageKey,
                                                       HttpStatus status) {
        ApiError apiError = new ApiError(status);
        apiError.setErrorMessageKey(errorMessageKey);
        apiError.setMessage(messageSource.getMessage(errorMessageKey, null, defaultMessage, LocaleContextHolder.getLocale()));
        apiError.setSubErrors(subErrors);
        return new ResponseEntity<>(apiError, apiError.getStatus());
    }
}
