package com.nithish.Enterprise.HR.Management.System.Dto;

import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class LeaveResponseDTO {
    private Long id;
    private LocalDate fromDate;
    private LocalDate toDate;
    private String reason;
    private Integer noOfDays;
    private String status;
    private String managerRemarks;
}
