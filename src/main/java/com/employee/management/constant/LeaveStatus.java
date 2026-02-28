package com.employee.management.constant;

import com.employee.management.exception.InvalidLeaveStateException;

/**
 * Assumption: Approved and rejected statuses are terminal
 */
public enum LeaveStatus {

    PENDING,
    APPROVED,
    REJECTED;

    public LeaveStatus transitionTo(LeaveStatus target) {
        if (this != PENDING) {
            throw new InvalidLeaveStateException(
                    "Only PENDING leave request can transition; current status: " + this);
        }
        if (target != APPROVED && target != REJECTED) {
            throw new InvalidLeaveStateException("Invalid transition target status: " + target);
        }
        return target;
    }
}
