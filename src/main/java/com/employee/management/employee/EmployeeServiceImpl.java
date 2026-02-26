package com.employee.management.employee;

import com.employee.management.constant.LeaveStatus;
import com.employee.management.employee.dto.EmployeeCreateRequest;
import com.employee.management.employee.dto.EmployeeUpdateRequest;
import com.employee.management.entity.employee.Employee;
import com.employee.management.entity.leave.LeaveRequest;
import com.employee.management.exception.ResourceNotFoundException;
import com.employee.management.leave.LeaveRepository;
import com.employee.management.leave.dto.LeaveCreateRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.List;

@Service
@RequiredArgsConstructor
public class EmployeeServiceImpl implements EmployeeService {

    private final EmployeeRepository employeeRepository;
    private final LeaveRepository leaveRepository;

    @Override
    @Transactional
    public Employee create(EmployeeCreateRequest request) {
        Employee employee = Employee.builder()
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .email(request.getEmail())
                .build();
        return employeeRepository.save(employee);
    }

    @Override
    public Page<Employee> getAll(Pageable pageable) {
        return employeeRepository.findAllByDeletedAtIsNull(pageable);
    }

    @Override
    public Employee getById(String id) {
        return employeeRepository.findByIdAndDeletedAtIsNull(id)
                .orElseThrow(() -> new ResourceNotFoundException("Employee not found: " + id));
    }

    @Override
    @Transactional
    public Employee update(String id, EmployeeUpdateRequest request) {
        Employee employee = getById(id);
        if (request.getFirstName() != null) employee.setFirstName(request.getFirstName());
        if (request.getLastName() != null) employee.setLastName(request.getLastName());
        if (request.getEmail() != null) employee.setEmail(request.getEmail());
        return employeeRepository.save(employee);
    }

    @Override
    @Transactional
    public void deleteById(String id) {
        Employee employee = getById(id);
        employee.setDeletedAt(Instant.now());
        employeeRepository.save(employee);
    }

    @Override
    public List<LeaveRequest> getLeaveByEmployeeId(String employeeId) {
        getById(employeeId);
        return leaveRepository.findAllByEmployee_Id(employeeId);
    }

    @Override
    @Transactional
    public LeaveRequest createLeaveForEmployee(String employeeId, LeaveCreateRequest request) {
        Employee employee = getById(employeeId);
        LeaveRequest leave = LeaveRequest.builder()
                .startDate(request.getStartDate())
                .endDate(request.getEndDate())
                .type(request.getType())
                .status(LeaveStatus.PENDING)
                .employee(employee)
                .build();
        return leaveRepository.save(leave);
    }
}
