package com.employee.management.leave;

import com.employee.management.constant.LeaveStatus;
import com.employee.management.entity.leave.LeaveRequest;
import com.employee.management.exception.LeaveRequestNotFoundException;
import com.employee.management.leave.dto.LeaveResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class LeaveServiceImpl
        implements LeaveService {

    private final LeaveRepository leaveRepository;
    private final LeaveMapper leaveMapper;

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
        leave.setStatus(leave.getStatus()
                             .transitionTo(LeaveStatus.APPROVED));
        return leaveMapper.toResponse(leaveRepository.save(leave));
    }

    @Override
    @Transactional
    public LeaveResponse reject(String id) {
        LeaveRequest leave = leaveRepository.findById(id)
                                            .orElseThrow(LeaveRequestNotFoundException::new);
        leave.setStatus(leave.getStatus()
                             .transitionTo(LeaveStatus.REJECTED));
        return leaveMapper.toResponse(leaveRepository.save(leave));
    }

}
