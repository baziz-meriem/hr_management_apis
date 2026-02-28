package com.employee.management.leave.dto;

import com.employee.management.constant.LeaveStatus;
import com.employee.management.constant.LeaveType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

/**
 * API response for leave. Exposes only the fields required by the consumer;
 * not all entity fields are included.
 */
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LeaveResponse {

    private String id;
    private LocalDate startDate;
    private LocalDate endDate;
    private LeaveType type;
    private LeaveStatus status;
    private String employeeId;
}
