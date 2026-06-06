package com.nithish.Enterprise.HR.Management.System.Entity;

import com.nithish.Enterprise.HR.Management.System.Audit.Auditable;
import com.nithish.Enterprise.HR.Management.System.Enum.DocumentStatus;
import com.nithish.Enterprise.HR.Management.System.Enum.DocumentType;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "employee_documents")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EmployeeDocument extends Auditable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String fileName;

    private String filePath;

    @Enumerated(EnumType.STRING)
    private DocumentType documentType;

    @Enumerated(EnumType.STRING)
    private DocumentStatus status;

    private String remarks;

    @ManyToOne
    @JoinColumn(name = "employee_id")
    private Employee employee;
}