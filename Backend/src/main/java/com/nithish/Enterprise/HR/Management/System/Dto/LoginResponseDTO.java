package com.nithish.Enterprise.HR.Management.System.Dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class LoginResponseDTO {

    private String role;
    private Long employeeId;
}
