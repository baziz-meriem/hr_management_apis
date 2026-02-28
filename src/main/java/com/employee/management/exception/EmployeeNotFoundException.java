package com.employee.management.exception;

public class EmployeeNotFoundException
        extends RuntimeException {

    public EmployeeNotFoundException() {
        super("Employee not found");
    }

}
