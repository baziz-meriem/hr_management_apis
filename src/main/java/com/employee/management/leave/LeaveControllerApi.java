package com.employee.management.leave;

import com.employee.management.entity.leave.LeaveRequest;
import com.employee.management.leave.dto.LeaveCreateRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;

@Tag(name = "Leaves", description = "Leave request API")
public interface LeaveControllerApi {

    @Operation(summary = "Create leave", description = "Submits a new leave request for an employee")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Leave request created"),
            @ApiResponse(responseCode = "404", description = "Employee not found"),
            @ApiResponse(responseCode = "400", description = "Validation failed")
    })
    ResponseEntity<LeaveRequest> create(@RequestBody LeaveCreateRequest request);

    @Operation(summary = "Get leave by ID", description = "Returns a single leave request by id")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Leave request found"),
            @ApiResponse(responseCode = "404", description = "Leave not found")
    })
    ResponseEntity<LeaveRequest> getById(@Parameter(description = "Leave request UUID") String id);

    @Operation(summary = "Approve leave", description = "Approves a pending leave request")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Leave approved"),
            @ApiResponse(responseCode = "404", description = "Leave not found"),
            @ApiResponse(responseCode = "400", description = "Only PENDING leave can be approved")
    })
    ResponseEntity<LeaveRequest> approve(@Parameter(description = "Leave request UUID") String id);

    @Operation(summary = "Reject leave", description = "Rejects a pending leave request")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Leave rejected"),
            @ApiResponse(responseCode = "404", description = "Leave not found"),
            @ApiResponse(responseCode = "400", description = "Only PENDING leave can be rejected")
    })
    ResponseEntity<LeaveRequest> reject(@Parameter(description = "Leave request UUID") String id);
}
