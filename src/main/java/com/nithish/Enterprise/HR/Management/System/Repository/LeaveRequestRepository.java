package com.nithish.Enterprise.HR.Management.System.Repository;

import com.nithish.Enterprise.HR.Management.System.Entity.LeaveRequest;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LeaveRequestRepository extends JpaRepository<LeaveRequest, Long> {

    List<LeaveRequest> findByEmployeeId(Long employeeId);

}