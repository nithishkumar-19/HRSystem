package com.nithish.Enterprise.HR.Management.System.Repository;

import com.nithish.Enterprise.HR.Management.System.Dto.LeaveResponseDTO;
import com.nithish.Enterprise.HR.Management.System.Entity.LeaveRequest;
import com.nithish.Enterprise.HR.Management.System.Enum.LeaveStatus;
import com.nithish.Enterprise.HR.Management.System.Mapper.LeaveDTOMapper;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface LeaveRequestRepository extends JpaRepository<LeaveRequest, Long> {

    List<LeaveRequest> findByEmployeeId(Long employeeId);
    List<LeaveRequest> findByStatusAndFromDateAfter(
            LeaveStatus status,
            LocalDate date
    );

    List<LeaveRequest> findByStatusInAndFromDateAfter(
            List<LeaveStatus> statuses,
            LocalDate fromDate);



}