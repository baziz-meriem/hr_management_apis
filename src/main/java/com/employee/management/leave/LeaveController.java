package com.employee.management.leave;

import com.employee.management.entity.leave.LeaveRequest;
import com.employee.management.leave.dto.LeaveCreateRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/leaves")
@RequiredArgsConstructor
public class LeaveController {

    private final LeaveService leaveService;

    @PostMapping
    public ResponseEntity<LeaveRequest> create(@Valid @RequestBody LeaveCreateRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(leaveService.create(request));
    }

    @GetMapping("/{id}")
    public ResponseEntity<LeaveRequest> getById(@PathVariable String id) {
        return ResponseEntity.ok(leaveService.getById(id));
    }

    @PatchMapping("/{id}/approve")
    public ResponseEntity<LeaveRequest> approve(@PathVariable String id) {
        return ResponseEntity.ok(leaveService.approve(id));
    }

    @PatchMapping("/{id}/reject")
    public ResponseEntity<LeaveRequest> reject(@PathVariable String id) {
        return ResponseEntity.ok(leaveService.reject(id));
    }
}
