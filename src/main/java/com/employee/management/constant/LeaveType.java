package com.employee.management.constant;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum LeaveType {
    ANNUAL_LEAVE("annual leave"),
    SICK_LEAVE("sick leave"),
    UNPAID_LEAVE("unpaid leave");

    private final String leaveType;
}
