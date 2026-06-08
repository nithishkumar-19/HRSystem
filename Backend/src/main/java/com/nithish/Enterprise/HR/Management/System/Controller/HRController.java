package com.nithish.Enterprise.HR.Management.System.Controller;

import com.nithish.Enterprise.HR.Management.System.Entity.LeaveRequest;
import com.nithish.Enterprise.HR.Management.System.Service.LeaveService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/hr")
public class HRController {

    private final LeaveService leaveService;

    public HRController(LeaveService leaveService) {
        this.leaveService = leaveService;
    }

    @PutMapping("/leave-confirmation/{leaveId}")
    public LeaveRequest hrApproveLeave(@PathVariable Long leaveId, @RequestParam String remarks) {

        return leaveService.hrApproveLeave(leaveId, remarks);
    }
}
