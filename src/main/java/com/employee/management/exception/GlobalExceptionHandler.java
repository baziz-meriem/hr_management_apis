package com.employee.management.exception;

import com.employee.management.constant.ErrorMessageKey;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.ArrayList;
import java.util.List;

@RestControllerAdvice
public class GlobalExceptionHandler
        extends ResponseEntityExceptionHandler {

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(
            MethodArgumentNotValidException ex,
            HttpHeaders headers,
            HttpStatusCode status,
            WebRequest request) {

        List<ApiSubError> subErrors = getValidationErrors(
                ex.getBindingResult().getFieldErrors(),
                ex.getBindingResult().getGlobalErrors());

        return buildResponseEntity(subErrors, ErrorMessageKey.VALIDATION_FAILED, HttpStatus.BAD_REQUEST, null);
    }

    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(
            HttpMessageNotReadableException ex,
            HttpHeaders headers,
            HttpStatusCode status,
            WebRequest request) {
        return buildResponseEntity(null, ErrorMessageKey.INVALID_REQUEST_BODY, HttpStatus.BAD_REQUEST, null);
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<Object> handleMethodArgumentTypeMismatch(MethodArgumentTypeMismatchException ex) {
        return buildResponseEntity(null, ErrorMessageKey.INVALID_PARAMETER_VALUE, HttpStatus.BAD_REQUEST,
                ex.getName() + " has invalid value");
    }

    @ExceptionHandler({
            EmployeeNotFoundException.class,
            LeaveRequestNotFoundException.class
    })
    public ResponseEntity<Object> handleNotFound(RuntimeException ex) {
        return buildResponseEntity(null, ErrorMessageKey.RESOURCE_NOT_FOUND, HttpStatus.NOT_FOUND, ex.getMessage());
    }

    @ExceptionHandler(DuplicateResourceException.class)
    public ResponseEntity<Object> handleDuplicate(DuplicateResourceException ex) {
        return buildResponseEntity(null, ErrorMessageKey.RESOURCE_ALREADY_EXISTS, HttpStatus.CONFLICT, ex.getMessage());
    }

    @ExceptionHandler(InvalidLeaveStateException.class)
    public ResponseEntity<Object> handleInvalidLeaveState(InvalidLeaveStateException ex) {
        return buildResponseEntity(null, ErrorMessageKey.INVALID_LEAVE_STATE, HttpStatus.BAD_REQUEST, ex.getMessage());
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> handleAny(Exception ex) {
        return buildResponseEntity(null, ErrorMessageKey.INTERNAL_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR, null);
    }

    private List<ApiSubError> getValidationErrors(List<FieldError> fieldErrors,
                                                  List<ObjectError> globalErrors) {
        List<ApiSubError> subErrors = new ArrayList<>();
        fieldErrors.forEach(fe -> subErrors.add(new ApiSubError(fe.getField(), fe.getDefaultMessage())));
        globalErrors.forEach(ge -> subErrors.add(new ApiSubError("request", ge.getDefaultMessage())));
        return subErrors;
    }

    private ResponseEntity<Object> buildResponseEntity(List<ApiSubError> subErrors,
                                                       ErrorMessageKey errorKey,
                                                       HttpStatus status,
                                                       String messageOverride) {
        ApiError apiError = new ApiError(status);
        apiError.setErrorMessageKey(errorKey.toString());
        apiError.setMessage(messageOverride != null ? messageOverride : errorKey.getMessage());
        apiError.setSubErrors(subErrors);
        return new ResponseEntity<>(apiError, apiError.getStatus());
    }
}
