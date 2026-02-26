package com.employee.management.leave;

import com.employee.management.entity.leave.LeaveRequest;
import com.employee.management.leave.dto.LeaveCreateRequest;

public interface LeaveService {

    LeaveRequest create(LeaveCreateRequest request);

    LeaveRequest getById(String id);

    LeaveRequest approve(String id);

    LeaveRequest reject(String id);
}
