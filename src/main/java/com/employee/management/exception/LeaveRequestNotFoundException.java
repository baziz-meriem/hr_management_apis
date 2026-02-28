package com.employee.management.exception;

public class LeaveRequestNotFoundException
        extends RuntimeException {

    public LeaveRequestNotFoundException() {
        super("Leave request not found.");
    }

}
