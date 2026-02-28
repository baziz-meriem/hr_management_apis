package com.employee.management.leave.dto;

import com.employee.management.constant.LeaveStatus;
import com.employee.management.constant.LeaveType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Assumption : only these fields are needed by the consumer.
 */
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LeaveResponse {

    private String id;
    private LeaveType type;
    private LeaveStatus status;
    private String employeeId;

}
