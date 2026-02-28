package com.employee.management.employee;

import com.employee.management.employee.dto.EmploymentDetailsDto;
import com.employee.management.employee.dto.EmployeeResponse;
import com.employee.management.entity.employee.Employee;
import com.employee.management.entity.employee.EmploymentDetails;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface EmployeeMapper {

    @Mapping(target = "id", source = "employee.id")
    @Mapping(target = "firstName", source = "employee.firstName")
    @Mapping(target = "lastName", source = "employee.lastName")
    @Mapping(target = "email", source = "employee.email")
    @Mapping(target = "employmentDetails", source = "employmentDetails")
    EmployeeResponse toResponse(Employee employee, EmploymentDetails employmentDetails);

    EmploymentDetailsDto toDetailsDto(EmploymentDetails details);
}
