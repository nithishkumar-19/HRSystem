package com.nithish.Enterprise.HR.Management.System.Repository;

import com.nithish.Enterprise.HR.Management.System.Entity.Certification;
import com.nithish.Enterprise.HR.Management.System.Enum.CertificationStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CertificationRepository extends JpaRepository<Certification, Long> {
    List<Certification> findByEmployeeId(Long employeeId);

    List<Certification> findByStatusIn(List<CertificationStatus> status);

    List<Certification> findByIdAndStatusIn(long empId , List<CertificationStatus> status);
}
