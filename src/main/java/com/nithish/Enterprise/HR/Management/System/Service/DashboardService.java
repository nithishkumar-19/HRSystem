package com.nithish.Enterprise.HR.Management.System.Service;

import com.nithish.Enterprise.HR.Management.System.Dto.EmployeeDashboardDTO;
import com.nithish.Enterprise.HR.Management.System.Dto.HRDashboardDTO;
import com.nithish.Enterprise.HR.Management.System.Dto.ManagerDashboardDTO;
import com.nithish.Enterprise.HR.Management.System.Entity.Employee;
import com.nithish.Enterprise.HR.Management.System.Enum.CertificationStatus;
import com.nithish.Enterprise.HR.Management.System.Enum.DocumentStatus;
import com.nithish.Enterprise.HR.Management.System.Enum.EmployeeStatus;
import com.nithish.Enterprise.HR.Management.System.Enum.LeaveStatus;
import com.nithish.Enterprise.HR.Management.System.Exception.ResourceNotFoundException;
import com.nithish.Enterprise.HR.Management.System.Repository.CertificationRepository;
import com.nithish.Enterprise.HR.Management.System.Repository.EmployeeRepository;
import com.nithish.Enterprise.HR.Management.System.Repository.LeaveRequestRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DashboardService {

    private final EmployeeRepository employeeRepository;
    private final LeaveRequestRepository leaveRequestRepository;
    private final CertificationRepository certificationRepository;

    public DashboardService(EmployeeRepository employeeRepository, LeaveRequestRepository leaveRequestRepository, CertificationRepository certificationRepository) {
        this.employeeRepository = employeeRepository;
        this.leaveRequestRepository = leaveRequestRepository;
        this.certificationRepository = certificationRepository;
    }

    public HRDashboardDTO getHRDashboard() {

        List<Employee> employees = employeeRepository.findAll();

        long totalEmployees = employees.size();

        long onboardingCompleted = employees.stream()
                .filter(emp -> emp.getStatus() == EmployeeStatus.COMPLETED)
                .count();

        long onboardingPending = employees.stream()
                .filter(emp -> emp.getStatus() != EmployeeStatus.COMPLETED)
                .count();

        long pendingDocuments = employees.stream()
                .filter(emp -> emp.getDocuments() != null)
                .flatMap(emp -> emp.getDocuments().stream())
                .filter(doc -> doc.getStatus() == DocumentStatus.PENDING)
                .count();

        return HRDashboardDTO.builder()
                .totalEmployees(totalEmployees)
                .onboardingPending(onboardingPending)
                .onboardingCompleted(onboardingCompleted)
                .pendingDocuments(pendingDocuments)
                .build();
    }

    public EmployeeDashboardDTO getEmployeeDashboard(Long employeeId) {

        Employee employee = employeeRepository.findById(employeeId)
                        .orElseThrow(() -> new ResourceNotFoundException("Employee not found: " + employeeId));

        return EmployeeDashboardDTO.builder()
                .employeeCode(employee.getEmployeeCode())
                .employeeName(employee.getFirstName() + " " + employee.getLastName())
                .department(employee.getDepartment())
                .designation(employee.getDesignation())
                .remainingLeaves(employee.getLeaveBalance().getRemainingLeaves())
                .totalSkills((long) employee.getSkills().size())
                .totalDocuments((long) employee.getDocuments().size())
                .build();
    }

    public ManagerDashboardDTO getManagerDashboard() {

        long pendingLeaves = leaveRequestRepository.findAll()
                .stream()
                .filter(leave -> leave.getStatus() == LeaveStatus.PENDING)
                .count();

        long pendingCertifications = certificationRepository.findAll()
                .stream()
                .filter(cert -> cert.getStatus() == CertificationStatus.PENDING)
                .count();

        return ManagerDashboardDTO.builder()
                .pendingLeaves(pendingLeaves)
                .pendingCertifications(pendingCertifications)
                .build();
    }
}