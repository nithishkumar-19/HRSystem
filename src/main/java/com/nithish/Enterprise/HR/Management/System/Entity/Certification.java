package com.nithish.Enterprise.HR.Management.System.Entity;

import com.nithish.Enterprise.HR.Management.System.Audit.Auditable;
import com.nithish.Enterprise.HR.Management.System.Enum.CertificationStatus;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "certifications")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Certification extends Auditable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String certificationName;

    private String issuingOrganization;

    private String certificateFileName;

    private String certificateFilePath;

    @Enumerated(EnumType.STRING)
    private CertificationStatus status;

    private String managerRemarks;

    @ManyToOne
    @JoinColumn(name = "employee_id")
    private Employee employee;
}
