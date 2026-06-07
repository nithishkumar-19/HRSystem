package com.nithish.Enterprise.HR.Management.System.Controller;

import com.nithish.Enterprise.HR.Management.System.Dto.LeaveRequestDTO;
import com.nithish.Enterprise.HR.Management.System.Dto.LeaveResponseDTO;
import com.nithish.Enterprise.HR.Management.System.Entity.LeaveRequest;
import com.nithish.Enterprise.HR.Management.System.Service.LeaveService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/leave")
@CrossOrigin(origins = "http://localhost:3000")
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
    public List<LeaveResponseDTO> getEmployeeLeaves(@PathVariable Long employeeId) {

        return leaveService.getEmployeeLeaves(employeeId);
    }

    @GetMapping("/employee/requested-leaves/{employeeId}")
    public List<LeaveResponseDTO> getRequestedLeavesController(@PathVariable Long employeeId) {

        return leaveService.getRequestedLeaves(employeeId);
    }

    @PostMapping("apply-leave")
    public LeaveRequest applyLeave(@Valid @RequestBody LeaveRequestDTO dto) {

        return leaveService.applyLeave(dto);
    }

    @PutMapping("/approve/{leaveId}")
    public LeaveRequest approveLeave(@PathVariable Long leaveId, @RequestParam String remarks) {

        return leaveService.approveLeave(leaveId, remarks);
    }

    @PutMapping("/reject/{leaveId}")
    public LeaveRequest rejectLeave(@PathVariable Long leaveId, @RequestParam String remarks) {

        return leaveService.rejectLeave(leaveId, remarks);
    }

}