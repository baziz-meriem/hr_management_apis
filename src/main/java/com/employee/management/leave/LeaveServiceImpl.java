package com.employee.management.leave;

import com.employee.management.constant.LeaveStatus;
import com.employee.management.entity.employee.Employee;
import com.employee.management.employee.EmployeeRepository;
import com.employee.management.entity.leave.LeaveRequest;
import com.employee.management.exception.EmployeeNotFoundException;
import com.employee.management.exception.InvalidLeaveStateException;
import com.employee.management.exception.LeaveRequestNotFoundException;
import com.employee.management.leave.dto.LeaveCreateRequest;
import com.employee.management.leave.dto.LeaveResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class LeaveServiceImpl
        implements LeaveService {

    private final LeaveRepository leaveRepository;
    private final EmployeeRepository employeeRepository;
    private final LeaveMapper leaveMapper;

    @Override
    @Transactional
    public LeaveResponse create(LeaveCreateRequest request) {
        Employee employee = employeeRepository.findByIdAndDeletedAtIsNull(request.getEmployeeId())
                .orElseThrow(EmployeeNotFoundException::new);
        LeaveRequest leave = LeaveRequest.builder()
                .startDate(request.getStartDate())
                .endDate(request.getEndDate())
                .type(request.getType())
                .status(LeaveStatus.PENDING)
                .employee(employee)
                .build();
        LeaveRequest saved = leaveRepository.save(leave);
        return leaveMapper.toResponse(saved);
    }

    @Override
    public LeaveResponse getById(String id) {
        LeaveRequest leave = leaveRepository.findById(id)
                .orElseThrow(LeaveRequestNotFoundException::new);
        return leaveMapper.toResponse(leave);
    }

    @Override
    @Transactional
    public LeaveResponse approve(String id) {
        LeaveRequest leave = leaveRepository.findById(id)
                .orElseThrow(LeaveRequestNotFoundException::new);
        if (leave.getStatus() != LeaveStatus.PENDING) {
            throw new InvalidLeaveStateException(
                    "Only PENDING leave can be approved; current status: " + leave.getStatus());
        }
        leave.setStatus(LeaveStatus.APPROVED);
        LeaveRequest saved = leaveRepository.save(leave);
        return leaveMapper.toResponse(saved);
    }

    @Override
    @Transactional
    public LeaveResponse reject(String id) {
        LeaveRequest leave = leaveRepository.findById(id)
                .orElseThrow(LeaveRequestNotFoundException::new);
        if (leave.getStatus() != LeaveStatus.PENDING) {
            throw new InvalidLeaveStateException(
                    "Only PENDING leave can be rejected; current status: " + leave.getStatus());
        }
        leave.setStatus(LeaveStatus.REJECTED);
        LeaveRequest saved = leaveRepository.save(leave);
        return leaveMapper.toResponse(saved);
    }

}
