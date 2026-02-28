package com.employee.management.employee.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EmploymentDetailsDto {

    @NotNull(message = "Hire date is required")
    private LocalDate hireDate;

    @NotBlank(message = "Department is required")
    @Size(max = 255)
    private String department;

    @NotBlank(message = "Position is required")
    @Size(max = 255)
    private String position;
}
