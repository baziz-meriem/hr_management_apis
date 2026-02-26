package com.employee.management.exception;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.http.HttpStatus;

import java.time.Instant;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class ApiError {

    private Instant timestamp;
    private HttpStatus status;
    private String message;
    private String errorMessageKey;
    private List<ApiSubError> subErrors;

    public ApiError(HttpStatus status) {
        this.timestamp = Instant.now();
        this.status = status;
    }
}
