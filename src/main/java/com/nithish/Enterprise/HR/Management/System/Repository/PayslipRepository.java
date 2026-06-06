package com.nithish.Enterprise.HR.Management.System.Repository;

import com.nithish.Enterprise.HR.Management.System.Entity.Payslip;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PayslipRepository extends JpaRepository<Payslip, Long> {

    List<Payslip> findByEmployeeId(Long employeeId);
}
