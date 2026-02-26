package com.employee.management.leave;

import com.employee.management.entity.leave.LeaveRequest;
import com.employee.management.leave.dto.LeaveCreateRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;

@Tag(name = "Leaves", description = "Leave request API")
public interface LeaveController {

    @Operation(summary = "Create leave", description = "Submits a new leave request for an employee")
    ResponseEntity<LeaveRequest> create(@RequestBody LeaveCreateRequest request);

    @Operation(summary = "Get leave by ID", description = "Returns a single leave request by id")
    ResponseEntity<LeaveRequest> getById(@Parameter(description = "Leave request UUID") String id);

    @Operation(summary = "Approve leave", description = "Approves a pending leave request")
    ResponseEntity<LeaveRequest> approve(@Parameter(description = "Leave request UUID") String id);

    @Operation(summary = "Reject leave", description = "Rejects a pending leave request")
    ResponseEntity<LeaveRequest> reject(@Parameter(description = "Leave request UUID") String id);
}
