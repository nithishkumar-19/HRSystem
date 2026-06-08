package com.nithish.Enterprise.HR.Management.System.Entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Table(name = "payslips")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Payslip {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String month;

    private Integer year;

    private Double basicSalary;

    private Double allowances;

    private Double deductions;

    private Double netSalary;

    private String pdfPath;

    private LocalDate generatedDate;

    @ManyToOne
    @JoinColumn(name = "employee_id")
    private Employee employee;
}