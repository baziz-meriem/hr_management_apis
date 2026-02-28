package com.employee.management.validation;

import com.employee.management.leave.dto.LeaveCreateRequest;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.time.LocalDate;

public class ValidDateRangeValidator
        implements ConstraintValidator<ValidDateRange, LeaveCreateRequest> {

    @Override
    public boolean isValid(LeaveCreateRequest value,
                           ConstraintValidatorContext context) {
        if (value == null) {
            return true;
        }
        LocalDate start = value.getStartDate();
        LocalDate end = value.getEndDate();
        if (start == null || end == null) {
            return true;
        }
        LocalDate today = LocalDate.now();
        if (!start.isAfter(today)) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate("Leave start date must be after the current day")
                    .addPropertyNode("startDate")
                    .addConstraintViolation();
            return false;
        }
        if (end.isBefore(start)) {
            return false;
        }
        return true;
    }

}
