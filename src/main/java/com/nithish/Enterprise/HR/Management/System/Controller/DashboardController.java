package com.nithish.Enterprise.HR.Management.System.Controller;

import com.nithish.Enterprise.HR.Management.System.Dto.EmployeeDashboardDTO;
import com.nithish.Enterprise.HR.Management.System.Dto.HRDashboardDTO;
import com.nithish.Enterprise.HR.Management.System.Dto.ManagerDashboardDTO;
import com.nithish.Enterprise.HR.Management.System.Service.DashboardService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/dashboard")
public class DashboardController {

    private final DashboardService dashboardService;

    public DashboardController(DashboardService dashboardService) {

        this.dashboardService = dashboardService;
    }

    @GetMapping("/hr")
    public HRDashboardDTO getHRDashboard() {

        return dashboardService.getHRDashboard();
    }

    @GetMapping("/employee/{employeeId}")
    public EmployeeDashboardDTO getEmployeeDashboard(@PathVariable Long employeeId) {

        return dashboardService.getEmployeeDashboard(employeeId);
    }

    @GetMapping("/manager")
    public ManagerDashboardDTO getManagerDashboard() {

        return dashboardService.getManagerDashboard();
    }
}