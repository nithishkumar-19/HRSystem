package com.nithish.Enterprise.HR.Management.System.Dto;

import jakarta.validation.constraints.*;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CertificationDTO {

    @NotNull(message = "Employee ID required")
    private Long employeeId;

    @NotBlank(message = "Certification name required")
    private String certificationName;

    @NotBlank(message = "Organization required")
    private String issuingOrganization;

    @NotBlank(message = "File name required")
    private String certificateFileName;

    @NotBlank(message = "File path required")
    private String certificateFilePath;
}