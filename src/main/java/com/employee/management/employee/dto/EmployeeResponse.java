package com.employee.management.employee.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Assumption : only Personal information and employment details is needed by the consumer in this context. Compensation is managed separately.
 */
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EmployeeResponse {

    private String id;
    private String firstName;
    private String lastName;
    private String email;
    private EmploymentDetailsDto employmentDetails;

}
