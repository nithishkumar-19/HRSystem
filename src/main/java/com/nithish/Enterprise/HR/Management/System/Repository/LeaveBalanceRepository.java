package com.nithish.Enterprise.HR.Management.System.Repository;

import com.nithish.Enterprise.HR.Management.System.Entity.LeaveBalance;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LeaveBalanceRepository extends JpaRepository<LeaveBalance, Long> {
    boolean existsByEmployeeFirstName(String employeeName);
}
