package com.employee.management.exception;

public class InvalidLeaveStateException extends RuntimeException {

    public InvalidLeaveStateException(String message) {
        super(message);
    }
}
