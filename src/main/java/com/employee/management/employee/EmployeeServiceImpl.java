package com.employee.management.employee;

import com.employee.management.constant.LeaveStatus;
import com.employee.management.employee.dto.EmployeeCreateRequest;
import com.employee.management.employee.dto.EmployeeResponse;
import com.employee.management.employee.dto.EmployeeUpdateRequest;
import com.employee.management.entity.employee.Employee;
import com.employee.management.entity.leave.LeaveRequest;
import com.employee.management.exception.ResourceNotFoundException;
import com.employee.management.leave.LeaveRepository;
import com.employee.management.leave.dto.LeaveCreateRequest;
import com.employee.management.leave.dto.LeaveResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class EmployeeServiceImpl implements EmployeeService {

    private final EmployeeRepository employeeRepository;
    private final LeaveRepository leaveRepository;

    @Override
    @Transactional
    public EmployeeResponse create(EmployeeCreateRequest request) {
        Employee employee = Employee.builder()
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .email(request.getEmail())
                .build();
        Employee saved = employeeRepository.save(employee);
        return new EmployeeResponse(saved.getId(), saved.getFirstName(), saved.getLastName(), saved.getEmail());
    }

    @Override
    public Page<EmployeeResponse> getAll(Pageable pageable) {
        return employeeRepository.findAllByDeletedAtIsNull(pageable)
                .map(e -> new EmployeeResponse(e.getId(), e.getFirstName(), e.getLastName(), e.getEmail()));
    }

    @Override
    public EmployeeResponse getById(String id) {
        Employee employee = employeeRepository.findByIdAndDeletedAtIsNull(id)
                .orElseThrow(() -> new ResourceNotFoundException("Employee not found: " + id));
        return new EmployeeResponse(employee.getId(), employee.getFirstName(), employee.getLastName(), employee.getEmail());
    }

    @Override
    @Transactional
    public EmployeeResponse update(String id, EmployeeUpdateRequest request) {
        Employee employee = employeeRepository.findByIdAndDeletedAtIsNull(id)
                .orElseThrow(() -> new ResourceNotFoundException("Employee not found: " + id));
        if (request.getFirstName() != null) employee.setFirstName(request.getFirstName());
        if (request.getLastName() != null) employee.setLastName(request.getLastName());
        if (request.getEmail() != null) employee.setEmail(request.getEmail());
        Employee saved = employeeRepository.save(employee);
        return new EmployeeResponse(saved.getId(), saved.getFirstName(), saved.getLastName(), saved.getEmail());
    }

    @Override
    @Transactional
    public void deleteById(String id) {
        Employee employee = employeeRepository.findByIdAndDeletedAtIsNull(id)
                .orElseThrow(() -> new ResourceNotFoundException("Employee not found: " + id));
        employee.setDeletedAt(Instant.now());
        employeeRepository.save(employee);
    }

    @Override
    public List<LeaveResponse> getLeaveByEmployeeId(String employeeId) {
        employeeRepository.findByIdAndDeletedAtIsNull(employeeId)
                .orElseThrow(() -> new ResourceNotFoundException("Employee not found: " + employeeId));
        return leaveRepository.findAllByEmployee_Id(employeeId).stream()
                .map(l -> new LeaveResponse(l.getId(), l.getStartDate(), l.getEndDate(), l.getType(), l.getStatus(), l.getEmployee().getId()))
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public LeaveResponse createLeaveForEmployee(String employeeId, LeaveCreateRequest request) {
        Employee employee = employeeRepository.findByIdAndDeletedAtIsNull(employeeId)
                .orElseThrow(() -> new ResourceNotFoundException("Employee not found: " + employeeId));
        LeaveRequest leave = LeaveRequest.builder()
                .startDate(request.getStartDate())
                .endDate(request.getEndDate())
                .type(request.getType())
                .status(LeaveStatus.PENDING)
                .employee(employee)
                .build();
        LeaveRequest saved = leaveRepository.save(leave);
        return new LeaveResponse(saved.getId(), saved.getStartDate(), saved.getEndDate(), saved.getType(), saved.getStatus(), saved.getEmployee().getId());
    }
}
