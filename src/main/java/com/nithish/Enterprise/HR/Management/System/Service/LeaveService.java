package com.nithish.Enterprise.HR.Management.System.Service;

import com.nithish.Enterprise.HR.Management.System.Dto.LeaveRequestDTO;
import com.nithish.Enterprise.HR.Management.System.Dto.LeaveResponseDTO;
import com.nithish.Enterprise.HR.Management.System.Entity.Employee;
import com.nithish.Enterprise.HR.Management.System.Entity.LeaveBalance;
import com.nithish.Enterprise.HR.Management.System.Entity.LeaveRequest;
import com.nithish.Enterprise.HR.Management.System.Enum.LeaveStatus;
import com.nithish.Enterprise.HR.Management.System.Exception.ResourceNotFoundException;
import com.nithish.Enterprise.HR.Management.System.Mapper.LeaveDTOMapper;
import com.nithish.Enterprise.HR.Management.System.Repository.EmployeeRepository;
import com.nithish.Enterprise.HR.Management.System.Repository.LeaveBalanceRepository;
import com.nithish.Enterprise.HR.Management.System.Repository.LeaveRequestRepository;
import com.nithish.Enterprise.HR.Management.System.Repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.hibernate.type.descriptor.sql.internal.Scale6IntervalSecondDdlType;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.List;

import static com.nithish.Enterprise.HR.Management.System.Enum.Role.*;

@Service
public class LeaveService {

    private final EmployeeRepository employeeRepository;
    private final LeaveRequestRepository leaveRequestRepository;
    private final LeaveBalanceRepository leaveBalanceRepository;
    private final LeaveDTOMapper leaveDTOMapper;
    private final UserRepository userRepository;

    public LeaveService(EmployeeRepository employeeRepository, LeaveRequestRepository leaveRequestRepository, LeaveBalanceRepository leaveBalanceRepository, LeaveDTOMapper leaveDTOMapper, UserRepository userRepository) {
        this.employeeRepository = employeeRepository;
        this.leaveRequestRepository = leaveRequestRepository;
        this.leaveBalanceRepository = leaveBalanceRepository;
        this.leaveDTOMapper = leaveDTOMapper;
        this.userRepository = userRepository;
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

        leave.setStatus(LeaveStatus.MANAGER_REJECTED);
        leave.setManagerRemarks(remarks);

        return leaveRequestRepository.save(leave);
    }

    public LeaveRequest hrApproveLeave(Long leaveId, String remarks ) {

        LeaveRequest leave = leaveRequestRepository.findById(leaveId)
                .orElseThrow(() -> new ResourceNotFoundException("Leave not found: " + leaveId));

        //Rejected leave case:
        if (leave.getStatus() != LeaveStatus.MANAGER_APPROVED) {
            leave.setStatus(LeaveStatus.REJECTED);
            leave.setManagerRemarks(remarks);
        }
        //Approved CAse
        else{
            leave.setStatus(LeaveStatus.APPROVED);
            leave.setManagerRemarks(remarks);
            deductLeaveBalance(leaveId);
        }
        leaveRequestRepository.save(leave);
        return leave;
    }

    public List<LeaveResponseDTO> getRequestedLeaves(Long empId) {

        var Employee = employeeRepository.findById(empId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("User not found: " + empId));

        List<LeaveRequest> leaves;

        switch (Employee.getUser().getRole()) {

            case HR -> {
                leaves = leaveRequestRepository.findByStatusInAndFromDateAfter(
                        List.of(
                                LeaveStatus.MANAGER_APPROVED,
                                LeaveStatus.MANAGER_REJECTED
                        ),
                        LocalDate.now()
                );
            }

            case MANAGER -> {
                leaves = leaveRequestRepository
                        .findByStatusAndFromDateAfter(
                                LeaveStatus.PENDING,
                                LocalDate.now());
            }

            case EMPLOYEE -> {

                var employee = employeeRepository
                        .findById(empId)
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Employee not found for user: " + empId));

                leaves = leaveRequestRepository
                        .findByEmployeeId(employee.getId());
            }

            default -> throw new IllegalStateException(
                    "Unsupported role: " + Employee.getUser().getRole());
        }

        return leaveDTOMapper.toDto(leaves);
    }

    public LeaveRequest applyLeave(LeaveRequestDTO dto) {

        Employee employee = employeeRepository.findById(dto.getEmployeeId())
                .orElseThrow(() -> new ResourceNotFoundException("Employee not found"));
        var noOfLeaveDays = ChronoUnit.DAYS.between(
                dto.getFromDate(),
                dto.getToDate()
        ) + 1;
        LeaveRequest request = LeaveRequest.builder()
                .fromDate(dto.getFromDate())
                .toDate(dto.getToDate())
                .reason(dto.getReason())
                .noOfDays((int) noOfLeaveDays)
                .status(LeaveStatus.PENDING)
                .employee(employee)
                .build();

        return leaveRequestRepository.save(request);
    }

    public void deductLeaveBalance(Long leaveId) {

        LeaveRequest leave = leaveRequestRepository.findById(leaveId)
                        .orElseThrow(() -> new ResourceNotFoundException("Leave not found: " + leaveId));

        if (leave.getStatus() != LeaveStatus.APPROVED && leave.getStatus() != LeaveStatus.MANAGER_APPROVED)
            return;

        LeaveBalance balance = leave.getEmployee().getLeaveBalance();

        balance.setUsedLeaves(balance.getUsedLeaves() + leave.getNoOfDays());
        balance.setRemainingLeaves(balance.getRemainingLeaves() - leave.getNoOfDays());

        leaveBalanceRepository.save(balance);
    }

    public List<LeaveResponseDTO> getEmployeeLeaves(Long employeeId) {

        var leaves =  leaveRequestRepository.findByEmployeeId(employeeId);

        var result =  leaveDTOMapper.toDto(leaves);
        return result;
    }

    public List<LeaveRequest> getAllLeaves() {
        return leaveRequestRepository.findAll();
    }
}