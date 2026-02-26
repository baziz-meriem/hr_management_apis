package com.employee.management.leave.dto;

import com.employee.management.constant.LeaveType;
import com.employee.management.validation.ValidUuid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@ValidDateRange
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LeaveCreateRequest {

    @NotNull(message = "Start date is required")
    private LocalDate startDate;

    @NotNull(message = "End date is required")
    private LocalDate endDate;

    @NotNull(message = "Leave type is required")
    private LeaveType type;

    @NotBlank(message = "Employee id is required")
    @ValidUuid(message = "Employee id must be a valid UUID")
    private String employeeId;
}
