package com.nithish.Enterprise.HR.Management.System.Dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ManagerDashboardDTO {

    private Long pendingLeaves;

    private Long pendingCertifications;
}
