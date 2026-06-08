package com.nithish.Enterprise.HR.Management.System.Entity;

import com.nithish.Enterprise.HR.Management.System.Audit.Auditable;
import com.nithish.Enterprise.HR.Management.System.Enum.LeaveStatus;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Table(name = "leave_requests")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LeaveRequest extends Auditable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDate fromDate;

    private LocalDate toDate;

    private String reason;

    private Integer noOfDays;

    @Enumerated(EnumType.STRING)
    private LeaveStatus status;

    private String managerRemarks;

    @ManyToOne
    @JoinColumn(name = "employee_id")
    private Employee employee;
}