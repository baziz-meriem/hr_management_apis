package com.employee.management.employee;

import com.employee.management.employee.dto.EmployeeCreateRequest;
import com.employee.management.employee.dto.EmployeeResponse;
import com.employee.management.employee.dto.EmployeeUpdateRequest;
import com.employee.management.leave.dto.LeaveCreateRequest;
import com.employee.management.leave.dto.LeaveResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface EmployeeService {

    EmployeeResponse create(EmployeeCreateRequest request);

    Page<EmployeeResponse> getAll(Pageable pageable);

    EmployeeResponse getById(String id);

    EmployeeResponse update(String id, EmployeeUpdateRequest request);

    void deleteById(String id);

    List<LeaveResponse> getLeaveByEmployeeId(String employeeId);

    LeaveResponse createLeaveForEmployee(String employeeId, LeaveCreateRequest request);
}
