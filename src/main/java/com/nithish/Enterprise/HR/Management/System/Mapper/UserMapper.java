package com.nithish.Enterprise.HR.Management.System.Mapper;



import com.nithish.Enterprise.HR.Management.System.Dto.EmployeeResponse;

import com.nithish.Enterprise.HR.Management.System.Entity.Employee;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class UserMapper {
    public EmployeeResponse toDto(Employee entity) {

        if (entity == null) {
            return null;
        }


        EmployeeResponse dto = new EmployeeResponse();

        dto.setId(entity.getId());
        dto.setDepartment(entity.getDepartment());
        dto.setFirstName(entity.getFirstName());
        dto.setLastName(entity.getLastName());
        dto.setPersonalEmail(entity.getCompanyEmail());
        dto.setDesignation(entity.getDesignation());
        dto.setGender(entity.getGender());

        return dto;
    }

    public List<EmployeeResponse> toDto(List<Employee> entities) {
        return entities.stream()
                .map(this::toDto)
                .toList();
    }
}
