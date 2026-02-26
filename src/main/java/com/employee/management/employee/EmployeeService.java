package com.employee.management.employee;

import com.employee.management.employee.dto.EmployeeCreateRequest;
import com.employee.management.employee.dto.EmployeeUpdateRequest;
import com.employee.management.entity.employee.Employee;
import com.employee.management.entity.leave.LeaveRequest;
import com.employee.management.leave.dto.LeaveCreateRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface EmployeeService {

    Employee create(EmployeeCreateRequest request);

    Page<Employee> getAll(Pageable pageable);

    Employee getById(String id);

    Employee update(String id, EmployeeUpdateRequest request);

    void deleteById(String id);

    List<LeaveRequest> getLeaveByEmployeeId(String employeeId);

    LeaveRequest createLeaveForEmployee(String employeeId, LeaveCreateRequest request);
}
