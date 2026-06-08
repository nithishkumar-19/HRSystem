package com.nithish.Enterprise.HR.Management.System.Mapper;

import com.nithish.Enterprise.HR.Management.System.Dto.EmployeeResponse;
import com.nithish.Enterprise.HR.Management.System.Dto.PayslipDTO;
import com.nithish.Enterprise.HR.Management.System.Entity.Employee;
import com.nithish.Enterprise.HR.Management.System.Entity.Payslip;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class PayslipMapper {
    public PayslipDTO toDto(Payslip entity) {

        if (entity == null) {
            return null;
        }

        PayslipDTO dto = new PayslipDTO();

        dto.setEmployeeId(
                entity.getEmployee() != null
                        ? entity.getEmployee().getId()
                        : null);
        dto.setName(entity.getEmployee().getFirstName());
        dto.setMonth(entity.getMonth());
        dto.setYear(entity.getYear());
        dto.setBasicSalary(entity.getBasicSalary());
        dto.setAllowances(entity.getAllowances());
        dto.setDeductions(entity.getDeductions());

        return dto;
    }

    public List<PayslipDTO> toDto(List<Payslip> entities) {
        return entities.stream()
                .map(this::toDto)
                .toList();
    }
}
