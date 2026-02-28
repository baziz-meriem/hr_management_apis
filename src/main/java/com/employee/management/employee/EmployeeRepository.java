package com.employee.management.employee;

import com.employee.management.entity.employee.Employee;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface EmployeeRepository
        extends JpaRepository<Employee, String> {

    Page<Employee> findAllByDeletedAtIsNull(Pageable pageable);

    Optional<Employee> findByIdAndDeletedAtIsNull(String id);

    boolean existsByEmailAndDeletedAtIsNull(String email);

}
