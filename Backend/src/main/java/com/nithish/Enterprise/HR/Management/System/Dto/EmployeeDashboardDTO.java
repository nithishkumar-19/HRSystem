package com.nithish.Enterprise.HR.Management.System.Dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EmployeeDashboardDTO {

    private String employeeCode;

    private String employeeName;

    private String department;

    private String designation;

    private Integer remainingLeaves;

    private Long totalSkills;

    private Long totalDocuments;
}