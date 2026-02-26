package com.employee.management.common.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum LeaveStatus {
    PENDING("pending"),
    APPROVED("approved"),
    rejected("rejected");

    private final String status;
}
