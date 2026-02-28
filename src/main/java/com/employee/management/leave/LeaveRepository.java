package com.employee.management.leave;

import com.employee.management.entity.leave.LeaveRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LeaveRepository
        extends JpaRepository<LeaveRequest, String> {

    List<LeaveRequest> findAllByEmployeeId(String employeeId);

}
