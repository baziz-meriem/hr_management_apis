package com.employee.management.employee;

import com.employee.management.employee.dto.EmployeeCreateRequest;
import com.employee.management.employee.dto.EmployeeUpdateRequest;
import com.employee.management.entity.employee.Employee;
import com.employee.management.entity.leave.LeaveRequest;
import com.employee.management.leave.dto.LeaveCreateRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/employees")
@RequiredArgsConstructor
public class EmployeeController {

    private final EmployeeService employeeService;

    @PostMapping
    public ResponseEntity<Employee> create(@Valid @RequestBody EmployeeCreateRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(employeeService.create(request));
    }

    @GetMapping
    public ResponseEntity<Page<Employee>> getAll(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size) {
        int safeSize = Math.min(Math.max(1, size), 100);
        Pageable pageable = PageRequest.of(page, safeSize, Sort.by("firstName").ascending());
        return ResponseEntity.ok(employeeService.getAll(pageable));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Employee> getById(@PathVariable String id) {
        return ResponseEntity.ok(employeeService.getById(id));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Employee> update(@PathVariable String id,
                                          @Valid @RequestBody EmployeeUpdateRequest request) {
        return ResponseEntity.ok(employeeService.update(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable String id) {
        employeeService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}/leave")
    public ResponseEntity<List<LeaveRequest>> getLeaveByEmployeeId(@PathVariable String id) {
        return ResponseEntity.ok(employeeService.getLeaveByEmployeeId(id));
    }

    @PostMapping("/{id}/leave")
    public ResponseEntity<LeaveRequest> createLeaveForEmployee(@PathVariable String id,
                                                               @Valid @RequestBody LeaveCreateRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(employeeService.createLeaveForEmployee(id, request));
    }
}
