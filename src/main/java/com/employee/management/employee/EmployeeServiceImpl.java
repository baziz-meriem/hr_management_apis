package com.employee.management.employee;

import com.employee.management.constant.LeaveStatus;
import com.employee.management.employee.dto.EmployeeCreateRequest;
import com.employee.management.employee.dto.EmployeeResponse;
import com.employee.management.employee.dto.EmployeeUpdateRequest;
import com.employee.management.entity.employee.Employee;
import com.employee.management.entity.employee.EmploymentDetails;
import com.employee.management.entity.employee.EmploymentDetailsRepository;
import com.employee.management.entity.leave.LeaveRequest;
import com.employee.management.exception.DuplicateResourceException;
import com.employee.management.exception.EmployeeNotFoundException;
import com.employee.management.leave.LeaveDates;
import com.employee.management.leave.LeaveMapper;
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
public class EmployeeServiceImpl
        implements EmployeeService {

    private final EmployeeRepository employeeRepository;
    private final EmploymentDetailsRepository employmentDetailsRepository;
    private final LeaveRepository leaveRepository;
    private final EmployeeMapper employeeMapper;
    private final LeaveMapper leaveMapper;

    @Override
    @Transactional
    public EmployeeResponse create(EmployeeCreateRequest request) {
        if (employeeRepository.existsByEmailAndDeletedAtIsNull(request.getEmail())) {
            throw new DuplicateResourceException("Employee with this email already exists");
        }
        var detailsReq = request.getEmploymentDetails();
        Employee employee = employeeRepository.save(Employee.builder()
                                                            .firstName(request.getFirstName())
                                                            .lastName(request.getLastName())
                                                            .email(request.getEmail())
                                                            .build());

        EmploymentDetails details = employmentDetailsRepository.save(EmploymentDetails.builder()
                                                                                      .hireDate(detailsReq.getHireDate())
                                                                                      .departement(detailsReq.getDepartement())
                                                                                      .position(detailsReq.getPosition())
                                                                                      .employee(employee)
                                                                                      .build());
        employee.setEmploymentDetails(details);

        return toResponse(employee);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<EmployeeResponse> getAll(Pageable pageable) {
        return employeeRepository.findAllByDeletedAtIsNull(pageable)
                                 .map(this::toResponse);
    }

    @Override
    @Transactional(readOnly = true)
    public EmployeeResponse getById(String id) {
        Employee employee = employeeRepository.findByIdAndDeletedAtIsNull(id)
                                              .orElseThrow(EmployeeNotFoundException::new);
        return toResponse(employee);
    }

    @Override
    @Transactional
    public EmployeeResponse update(String id,
                                   EmployeeUpdateRequest request) {
        Employee employee = employeeRepository.findByIdAndDeletedAtIsNull(id)
                                              .orElseThrow(EmployeeNotFoundException::new);
        if (request.getFirstName() != null) {
            employee.setFirstName(request.getFirstName());
        }
        if (request.getLastName() != null) {
            employee.setLastName(request.getLastName());
        }
        if (request.getEmail() != null) {
            employee.setEmail(request.getEmail());
        }
        employeeRepository.save(employee);

        var detailsReq = request.getEmploymentDetails();
        if (detailsReq != null) {
            EmploymentDetails details = employmentDetailsRepository.findByEmployeeIdAndDeletedAtIsNull(id)
                                                                   .orElse(null);
            if (details != null) {
                details.setHireDate(detailsReq.getHireDate());
                details.setDepartement(detailsReq.getDepartement());
                details.setPosition(detailsReq.getPosition());
                employmentDetailsRepository.save(details);
            } else {
                details = EmploymentDetails.builder()
                                           .hireDate(detailsReq.getHireDate())
                                           .departement(detailsReq.getDepartement())
                                           .position(detailsReq.getPosition())
                                           .employee(employee)
                                           .build();
                employmentDetailsRepository.save(details);
                employee.setEmploymentDetails(details);
            }
        }

        return toResponse(employee);
    }

    @Override
    @Transactional
    public void deleteById(String id) {
        Employee employee = employeeRepository.findByIdAndDeletedAtIsNull(id)
                                              .orElseThrow(EmployeeNotFoundException::new);
        employee.setDeletedAt(Instant.now());
        employeeRepository.save(employee);
    }

    @Override
    public List<LeaveResponse> getLeaveByEmployeeId(String employeeId) {
        List<LeaveRequest> leaves = leaveRepository.findAllByEmployeeIdAndDeletedAtIsNull(employeeId);
        return leaves.stream()
                     .map(leaveMapper::toResponse)
                     .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public LeaveResponse createLeaveForEmployee(String employeeId,
                                                LeaveCreateRequest request) {
        Employee employee = employeeRepository.findByIdAndDeletedAtIsNull(employeeId)
                                              .orElseThrow(EmployeeNotFoundException::new);

        var effectiveEndDate = LeaveDates.extendToEndOfWeek(request.getEndDate());
        if (leaveRepository.existsOverlappingLeave(employeeId,
                                                   request.getStartDate(),
                                                   effectiveEndDate,
                                                   List.of(LeaveStatus.PENDING,
                                                           LeaveStatus.APPROVED))) {
            throw new DuplicateResourceException("Leave already exists for this employee with overlapping dates");
        }
        LeaveRequest leave = LeaveRequest.builder()
                                         .startDate(request.getStartDate())
                                         .endDate(effectiveEndDate)
                                         .type(request.getType())
                                         .employee(employee)
                                         .build();
        LeaveRequest saved = leaveRepository.save(leave);
        return leaveMapper.toResponse(saved);
    }

    private EmployeeResponse toResponse(Employee employee) {
        EmploymentDetails details = employmentDetailsRepository.findByEmployeeIdAndDeletedAtIsNull(employee.getId())
                                                               .orElse(null);
        return employeeMapper.toResponse(employee,
                                         details);
    }

}
