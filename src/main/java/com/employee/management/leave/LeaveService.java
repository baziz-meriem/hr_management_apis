package com.employee.management.leave;

import com.employee.management.leave.dto.LeaveCreateRequest;
import com.employee.management.leave.dto.LeaveResponse;

public interface LeaveService {

    LeaveResponse create(LeaveCreateRequest request);

    LeaveResponse getById(String id);

    LeaveResponse approve(String id);

    LeaveResponse reject(String id);
}
