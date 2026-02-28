package com.employee.management.leave;

import java.time.DayOfWeek;
import java.time.LocalDate;

/**
 * Assumption : If the leave end date is Thursday, it is extended to Saturday.
 * Any other end day is left unchanged.
 */
public final class LeaveDates {

    private LeaveDates() {
    }

    public static LocalDate extendToEndOfWeek(LocalDate endDate) {
        if (endDate == null) return null;
        if (endDate.getDayOfWeek() == DayOfWeek.THURSDAY) {
            return endDate.plusDays(2);
        }
        return endDate;
    }
}
