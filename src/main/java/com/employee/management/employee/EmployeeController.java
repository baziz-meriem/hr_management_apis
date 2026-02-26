package com.employee.management.employee;

import com.employee.management.employee.dto.EmployeeCreateRequest;
import com.employee.management.employee.dto.EmployeeResponse;
import com.employee.management.employee.dto.EmployeeUpdateRequest;
import com.employee.management.leave.dto.LeaveCreateRequest;
import com.employee.management.leave.dto.LeaveResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Tag(name = "Employees", description = "Employee management API")
public interface EmployeeController {

    @Operation(summary = "Create employee", description = "Creates a new employee with personal information")
    ResponseEntity<EmployeeResponse> create(@RequestBody EmployeeCreateRequest request);

    @Operation(summary = "List employees", description = "Returns a paginated list of active employees")
    ResponseEntity<Page<EmployeeResponse>> getAll(
            @Parameter(description = "Zero-based page index") @RequestParam(defaultValue = "0") int page,
            @Parameter(description = "Page size (1–100)") @RequestParam(defaultValue = "20") int size);

    @Operation(summary = "Get employee by ID", description = "Returns a single employee by id")
    ResponseEntity<EmployeeResponse> getById(@Parameter(description = "Employee UUID") String id);

    @Operation(summary = "Update employee", description = "Partially updates an employee")
    ResponseEntity<EmployeeResponse> update(String id, @RequestBody EmployeeUpdateRequest request);

    @Operation(summary = "Delete employee", description = "Soft-deletes an employee")
    ResponseEntity<Void> deleteById(@Parameter(description = "Employee UUID") String id);

    @Operation(summary = "Get leave by employee", description = "Returns all leave requests for an employee")
    ResponseEntity<List<LeaveResponse>> getLeaveByEmployeeId(@Parameter(description = "Employee UUID") String id);

    @Operation(summary = "Create leave for employee", description = "Submits a new leave request for an employee")
    ResponseEntity<LeaveResponse> createLeaveForEmployee(String id, @RequestBody LeaveCreateRequest request);
}
