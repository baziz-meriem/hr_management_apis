package com.employee.management.leave;

import com.employee.management.constant.LeaveStatus;
import com.employee.management.entity.leave.LeaveRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface LeaveRepository
        extends JpaRepository<LeaveRequest, String> {

    List<LeaveRequest> findAllByEmployeeIdAndDeletedAtIsNull(String employeeId);

    @Query("SELECT COUNT(l) > 0 FROM LeaveRequest l WHERE l.employee.id = :employeeId " +
            "AND l.status IN :statuses AND l.startDate <= :endDate AND l.endDate >= :startDate AND l.deletedAt IS NULL")
    boolean existsOverlappingLeave(@Param("employeeId") String employeeId,
                                   @Param("startDate") LocalDate startDate,
                                   @Param("endDate") LocalDate endDate,
                                   @Param("statuses") List<LeaveStatus> statuses);
}
