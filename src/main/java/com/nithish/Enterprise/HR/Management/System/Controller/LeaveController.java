package com.nithish.Enterprise.HR.Management.System.Controller;

import com.nithish.Enterprise.HR.Management.System.Dto.LeaveRequestDTO;
import com.nithish.Enterprise.HR.Management.System.Entity.LeaveRequest;
import com.nithish.Enterprise.HR.Management.System.Service.LeaveService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/leave")
public class LeaveController {

    private final LeaveService leaveService;

    public LeaveController(LeaveService leaveService) {
        this.leaveService = leaveService;
    }

    @GetMapping("/all")
    public List<LeaveRequest> getAllLeaves() {
        return leaveService.getAllLeaves();
    }

    @GetMapping("/employee/{employeeId}")
    public List<LeaveRequest> getEmployeeLeaves(@PathVariable Long employeeId) {

        return leaveService.getEmployeeLeaves(employeeId);
    }

    @PostMapping
    public LeaveRequest applyLeave(@Valid @RequestBody LeaveRequestDTO dto) {

        return leaveService.applyLeave(dto);
    }

    @PutMapping("/approve/{leaveId}")
    public LeaveRequest approveLeave(@PathVariable Long leaveId, @RequestParam String remarks) {

        return leaveService.approveLeave(leaveId, remarks);
    }

    @PutMapping("/hr-approve/{leaveId}")
    public LeaveRequest hrApproveLeave(@PathVariable Long leaveId, @RequestParam String remarks) {

        return leaveService.hrApproveLeave(leaveId, remarks);
    }

    @PutMapping("/reject/{leaveId}")
    public LeaveRequest rejectLeave(@PathVariable Long leaveId, @RequestParam String remarks) {

        return leaveService.rejectLeave(leaveId, remarks);
    }

}