package com.nithish.Enterprise.HR.Management.System.Service;

import com.nithish.Enterprise.HR.Management.System.Dto.PayslipDTO;
import com.nithish.Enterprise.HR.Management.System.Entity.Employee;
import com.nithish.Enterprise.HR.Management.System.Entity.Payslip;
import com.nithish.Enterprise.HR.Management.System.Exception.ResourceNotFoundException;
import com.nithish.Enterprise.HR.Management.System.Repository.EmployeeRepository;
import com.nithish.Enterprise.HR.Management.System.Repository.PayslipRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class PayslipService {

    private final PayslipRepository payslipRepository;
    private final EmployeeRepository employeeRepository;

    public PayslipService(PayslipRepository payslipRepository, EmployeeRepository employeeRepository) {
        this.payslipRepository = payslipRepository;
        this.employeeRepository = employeeRepository;
    }

    public Payslip generatePayslip(PayslipDTO dto) {

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

        return payslipRepository.save(payslip);
    }

    public List<Payslip> getEmployeePayslips(Long employeeId) {
        return payslipRepository.findByEmployeeId(employeeId);
    }
}