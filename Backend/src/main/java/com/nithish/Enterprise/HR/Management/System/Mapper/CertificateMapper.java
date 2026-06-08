package com.nithish.Enterprise.HR.Management.System.Mapper;

import com.nithish.Enterprise.HR.Management.System.Dto.CertificateResponse;
import com.nithish.Enterprise.HR.Management.System.Dto.LeaveResponseDTO;
import com.nithish.Enterprise.HR.Management.System.Entity.Certification;
import com.nithish.Enterprise.HR.Management.System.Entity.LeaveRequest;
import com.nithish.Enterprise.HR.Management.System.Enum.CertificationStatus;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class CertificateMapper {
    public CertificateResponse toDto(Certification entity) {

        if (entity == null) {
            return null;
        }

        CertificateResponse dto = new CertificateResponse();

        dto.setId(entity.getId());
        dto.setEmployeeName(entity.getEmployee().getFirstName());
        dto.setCertificationName(entity.getCertificationName());
        dto.setIssuingOrganization(entity.getIssuingOrganization());
        dto.setCertificateFileName(entity.getFileName());
        dto.setStatus(entity.getStatus());

        return dto;
    }
    public List<CertificateResponse> toDto(List<Certification> entities) {
        return entities.stream()
                .map(this::toDto)
                .toList();
    }
}

