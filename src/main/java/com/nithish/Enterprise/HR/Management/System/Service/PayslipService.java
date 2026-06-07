package com.nithish.Enterprise.HR.Management.System.Service;

import com.nithish.Enterprise.HR.Management.System.Dto.PayslipDTO;
import com.nithish.Enterprise.HR.Management.System.Entity.Employee;
import com.nithish.Enterprise.HR.Management.System.Entity.Payslip;
import com.nithish.Enterprise.HR.Management.System.Exception.ResourceNotFoundException;
import com.nithish.Enterprise.HR.Management.System.Mapper.PayslipMapper;
import com.nithish.Enterprise.HR.Management.System.Repository.EmployeeRepository;
import com.nithish.Enterprise.HR.Management.System.Repository.PayslipRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PayslipService {

    private final PayslipRepository payslipRepository;
    private final EmployeeRepository employeeRepository;
    private final PayslipMapper payslipMapper;


    public PayslipDTO generatePayslip(PayslipDTO dto) {

        Employee employee = employeeRepository.findById(dto.getEmployeeId())
                        .orElseThrow(() -> new ResourceNotFoundException("Employee not found: " + dto.getEmployeeId()));

        double netSalary = dto.getBasicSalary() + dto.getAllowances() - dto.getDeductions();

        Payslip payslip = Payslip.builder()
                .month(dto.getMonth())
                .year(dto.getYear())
                .basicSalary(dto.getBasicSalary())
                .allowances(dto.getAllowances())
                .deductions(dto.getDeductions())
                .netSalary(netSalary)
                .pdfPath("generated/payslip_" + employee.getEmployeeCode() + ".pdf")
                .generatedDate(LocalDate.now())
                .employee(employee)
                .build();

        var result = payslipRepository.save(payslip);
        return payslipMapper.toDto(result);
    }

    public List<PayslipDTO> getEmployeePayslips(Long employeeId) {
        return payslipMapper.toDto(payslipRepository.findByEmployeeId(employeeId));
    }
}