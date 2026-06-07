package com.nithish.Enterprise.HR.Management.System.Mapper;

import com.nithish.Enterprise.HR.Management.System.Dto.LeaveResponseDTO;
import com.nithish.Enterprise.HR.Management.System.Entity.LeaveRequest;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class LeaveDTOMapper {
    public LeaveResponseDTO toDto(LeaveRequest entity) {

        if (entity == null) {
            return null;
        }

        LeaveResponseDTO dto = new LeaveResponseDTO();

        dto.setId(entity.getId());
        dto.setFromDate(entity.getFromDate());
        dto.setToDate(entity.getToDate());
        dto.setReason(entity.getReason());
        dto.setNoOfDays(entity.getNoOfDays());

        dto.setStatus(
                entity.getStatus() != null
                        ? entity.getStatus().name()
                        : null
        );

        dto.setManagerRemarks(entity.getManagerRemarks());

        return dto;
    }
    public List<LeaveResponseDTO> toDto(List<LeaveRequest> entities) {
        return entities.stream()
                .map(this::toDto)
                .toList();
    }
}
