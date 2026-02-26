package com.employee.management.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum LeaveStatus {
    PENDING("pending"),
    APPROVED("approved"),
    REJECTED("rejected");

    private final String status;
}
