package com.employee.management.entity.employee;

import com.employee.management.entity.BaseEntity;
import com.employee.management.entity.leave.LeaveRequest;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.UuidGenerator;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Employee
        extends BaseEntity {

    @Id
    @UuidGenerator
    private String id;
    private String firstName;
    private String lastName;
    private String email;

    @OneToOne(mappedBy = "employee")
    private EmploymentDetails employmentDetails;


    @OneToMany(mappedBy = "employee", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<LeaveRequest> leaveRequests = new ArrayList<>();
}
