package com.employee.management.leave.dto;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.time.LocalDate;

public class ValidDateRangeValidator implements ConstraintValidator<ValidDateRange, LeaveCreateRequest> {

    @Override
    public boolean isValid(LeaveCreateRequest value, ConstraintValidatorContext context) {
        if (value == null) return true;
        LocalDate start = value.getStartDate();
        LocalDate end = value.getEndDate();
        if (start == null || end == null) return true;
        return !end.isBefore(start);
    }
}
