package com.nithish.Enterprise.HR.Management.System.Dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class HRDashboardDTO {

    private Long totalEmployees;

    private Long onboardingPending;

    private Long onboardingCompleted;

    private Long pendingDocuments;
}