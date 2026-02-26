package com.employee.management.leave;

import com.employee.management.constant.LeaveStatus;
import com.employee.management.entity.employee.Employee;
import com.employee.management.employee.EmployeeRepository;
import com.employee.management.entity.leave.LeaveRequest;
import com.employee.management.exception.InvalidLeaveStateException;
import com.employee.management.exception.ResourceNotFoundException;
import com.employee.management.leave.dto.LeaveCreateRequest;
import com.employee.management.leave.dto.LeaveResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class LeaveServiceImpl implements LeaveService {

    private final LeaveRepository leaveRepository;
    private final EmployeeRepository employeeRepository;

    @Override
    @Transactional
    public LeaveResponse create(LeaveCreateRequest request) {
        Employee employee = employeeRepository.findByIdAndDeletedAtIsNull(request.getEmployeeId())
                .orElseThrow(() -> new ResourceNotFoundException("Employee not found: " + request.getEmployeeId()));
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

    @Override
    public LeaveResponse getById(String id) {
        LeaveRequest leave = leaveRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Leave not found: " + id));
        return new LeaveResponse(leave.getId(), leave.getStartDate(), leave.getEndDate(), leave.getType(), leave.getStatus(), leave.getEmployee().getId());
    }

    @Override
    @Transactional
    public LeaveResponse approve(String id) {
        LeaveRequest leave = leaveRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Leave not found: " + id));
        if (leave.getStatus() != LeaveStatus.PENDING) {
            throw new InvalidLeaveStateException(
                    "Only PENDING leave can be approved; current status: " + leave.getStatus());
        }
        leave.setStatus(LeaveStatus.APPROVED);
        LeaveRequest saved = leaveRepository.save(leave);
        return new LeaveResponse(saved.getId(), saved.getStartDate(), saved.getEndDate(), saved.getType(), saved.getStatus(), saved.getEmployee().getId());
    }

    @Override
    @Transactional
    public LeaveResponse reject(String id) {
        LeaveRequest leave = leaveRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Leave not found: " + id));
        if (leave.getStatus() != LeaveStatus.PENDING) {
            throw new InvalidLeaveStateException(
                    "Only PENDING leave can be rejected; current status: " + leave.getStatus());
        }
        leave.setStatus(LeaveStatus.REJECTED);
        LeaveRequest saved = leaveRepository.save(leave);
        return new LeaveResponse(saved.getId(), saved.getStartDate(), saved.getEndDate(), saved.getType(), saved.getStatus(), saved.getEmployee().getId());
    }
}
