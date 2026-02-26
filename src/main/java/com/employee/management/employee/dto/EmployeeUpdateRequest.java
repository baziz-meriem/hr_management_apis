package com.employee.management.employee.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EmployeeUpdateRequest {

    @Size(max = 255)
    private String firstName;

    @Size(max = 255)
    private String lastName;

    @Email(message = "Email must be valid")
    @Size(max = 255)
    private String email;
}
