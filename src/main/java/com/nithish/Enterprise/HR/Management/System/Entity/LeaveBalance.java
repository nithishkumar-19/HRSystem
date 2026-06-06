package com.nithish.Enterprise.HR.Management.System.Entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "leave_balances")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LeaveBalance {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Integer totalLeaves;

    private Integer usedLeaves;

    private Integer remainingLeaves;

    @OneToOne
    @JoinColumn(name = "employee_id")
    private Employee employee;
}