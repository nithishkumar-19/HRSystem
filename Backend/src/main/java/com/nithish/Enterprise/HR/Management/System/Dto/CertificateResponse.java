package com.nithish.Enterprise.HR.Management.System.Dto;

import com.nithish.Enterprise.HR.Management.System.Enum.CertificationStatus;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CertificateResponse {


    @NotNull(message = "Employee ID required")
    private Long id;


    private String EmployeeName;

    @NotBlank(message = "Certification name required")
    private String certificationName;

    @NotBlank(message = "Organization required")
    private String issuingOrganization;

    @NotBlank(message = "File name required")
    private String certificateFileName;

    private CertificationStatus status ;
}
