package com.employee.management.leave;

import com.employee.management.leave.dto.LeaveResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;

@Tag(name = "Leaves", description = "Leave request API")
public interface LeaveController {

    @Operation(summary = "Get leave by ID", description = "Returns a single leave request by id")
    ResponseEntity<LeaveResponse> getById(@Parameter(description = "Leave request UUID") String id);

    @Operation(summary = "Approve leave", description = "Approves a pending leave request")
    ResponseEntity<LeaveResponse> approve(@Parameter(description = "Leave request UUID") String id);

    @Operation(summary = "Reject leave", description = "Rejects a pending leave request")
    ResponseEntity<LeaveResponse> reject(@Parameter(description = "Leave request UUID") String id);
}
