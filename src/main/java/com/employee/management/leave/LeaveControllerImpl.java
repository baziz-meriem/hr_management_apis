package com.employee.management.leave;

import com.employee.management.leave.dto.LeaveResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping(value = "/leaves", headers = "X-API-Version=1")
@RequiredArgsConstructor
public class LeaveControllerImpl implements LeaveController {

    private final LeaveService leaveService;

    @Override
    @GetMapping("/{id}")
    public ResponseEntity<LeaveResponse> getById(@PathVariable UUID id) {
        return ResponseEntity.ok(leaveService.getById(id.toString()));
    }

    @Override
    @PatchMapping("/{id}/approve")
    public ResponseEntity<LeaveResponse> approve(@PathVariable UUID id) {
        return ResponseEntity.ok(leaveService.approve(id.toString()));
    }

    @Override
    @PatchMapping("/{id}/reject")
    public ResponseEntity<LeaveResponse> reject(@PathVariable UUID id) {
        return ResponseEntity.ok(leaveService.reject(id.toString()));
    }
}
