package com.employee.management.employee;

import com.employee.management.employee.dto.EmployeeResponse;
import com.employee.management.entity.employee.Employee;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface EmployeeMapper {

    EmployeeResponse toResponse(Employee employee);
}
