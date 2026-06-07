package com.nithish.Enterprise.HR.Management.System.Controller;

import com.nithish.Enterprise.HR.Management.System.Dto.PayslipDTO;
import com.nithish.Enterprise.HR.Management.System.Entity.Payslip;
import com.nithish.Enterprise.HR.Management.System.Service.PayslipService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/payslip")
public class PayslipController {

    private final PayslipService payslipService;

    public PayslipController(PayslipService payslipService) {
        this.payslipService = payslipService;
    }

    @PostMapping("/generate")
    public PayslipDTO generatePayslip(@RequestBody PayslipDTO dto) {
        return payslipService.generatePayslip(dto);
    }

    @GetMapping("/employee/{employeeId}")
    public List<PayslipDTO> getPayslips(@PathVariable Long employeeId) {
        return payslipService.getEmployeePayslips(employeeId);
    }
}