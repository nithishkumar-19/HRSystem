package com.nithish.Enterprise.HR.Management.System.Service;

import com.nithish.Enterprise.HR.Management.System.Dto.LeaveRequestDTO;
import com.nithish.Enterprise.HR.Management.System.Entity.Employee;
import com.nithish.Enterprise.HR.Management.System.Entity.LeaveBalance;
import com.nithish.Enterprise.HR.Management.System.Entity.LeaveRequest;
import com.nithish.Enterprise.HR.Management.System.Enum.LeaveStatus;
import com.nithish.Enterprise.HR.Management.System.Exception.ResourceNotFoundException;
import com.nithish.Enterprise.HR.Management.System.Repository.EmployeeRepository;
import com.nithish.Enterprise.HR.Management.System.Repository.LeaveBalanceRepository;
import com.nithish.Enterprise.HR.Management.System.Repository.LeaveRequestRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LeaveService {

    private final EmployeeRepository employeeRepository;
    private final LeaveRequestRepository leaveRequestRepository;
    private final LeaveBalanceRepository leaveBalanceRepository;

    public LeaveService(EmployeeRepository employeeRepository, LeaveRequestRepository leaveRequestRepository, LeaveBalanceRepository leaveBalanceRepository) {
        this.employeeRepository = employeeRepository;
        this.leaveRequestRepository = leaveRequestRepository;
        this.leaveBalanceRepository = leaveBalanceRepository;
    }

    public LeaveRequest approveLeave(Long leaveId, String remarks) {

        LeaveRequest leave = leaveRequestRepository.findById(leaveId)
                        .orElseThrow(() -> new ResourceNotFoundException("Leave not found: " + leaveId));

        leave.setStatus(LeaveStatus.MANAGER_APPROVED);
        leave.setManagerRemarks(remarks);

        return leaveRequestRepository.save(leave);
    }

    public LeaveRequest rejectLeave(Long leaveId, String remarks) {

        LeaveRequest leave = leaveRequestRepository.findById(leaveId)
                        .orElseThrow(() -> new ResourceNotFoundException("Leave not found: " + leaveId));

        leave.setStatus(LeaveStatus.REJECTED);
        leave.setManagerRemarks(remarks);

        return leaveRequestRepository.save(leave);
    }

    public LeaveRequest hrApproveLeave(Long leaveId, String remarks) {

        LeaveRequest leave = leaveRequestRepository.findById(leaveId)
                .orElseThrow(() -> new ResourceNotFoundException("Leave not found: " + leaveId));

        if (leave.getStatus() != LeaveStatus.MANAGER_APPROVED) {
            throw new RuntimeException("Manager approval required first");
        }

        leave.setStatus(LeaveStatus.APPROVED);
        leave.setManagerRemarks(remarks);

        leaveRequestRepository.save(leave);

        deductLeaveBalance(leaveId);

        return leave;
    }

    public LeaveRequest applyLeave(LeaveRequestDTO dto) {

        Employee employee = employeeRepository.findById(dto.getEmployeeId())
                .orElseThrow(() -> new ResourceNotFoundException("Employee not found"));

        LeaveRequest request = LeaveRequest.builder()
                .fromDate(dto.getFromDate())
                .toDate(dto.getToDate())
                .reason(dto.getReason())
                .noOfDays(dto.getNoOfDays())
                .status(LeaveStatus.PENDING)
                .employee(employee)
                .build();

        return leaveRequestRepository.save(request);
    }

    public void deductLeaveBalance(Long leaveId) {

        LeaveRequest leave = leaveRequestRepository.findById(leaveId)
                        .orElseThrow(() -> new ResourceNotFoundException("Leave not found: " + leaveId));

        if (leave.getStatus() != LeaveStatus.APPROVED)
            return;

        LeaveBalance balance = leave.getEmployee().getLeaveBalance();

        balance.setUsedLeaves(balance.getUsedLeaves() + leave.getNoOfDays());
        balance.setRemainingLeaves(balance.getRemainingLeaves() - leave.getNoOfDays());

        leaveBalanceRepository.save(balance);
    }

    public List<LeaveRequest> getEmployeeLeaves(Long employeeId) {

        return leaveRequestRepository.findByEmployeeId(employeeId);
    }

    public List<LeaveRequest> getAllLeaves() {
        return leaveRequestRepository.findAll();
    }
}