package com.nithish.Enterprise.HR.Management.System.Dto;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EmployeeResponse {
    private Long id;

    private String firstName;

    private String lastName;

    private String department;

    private String designation;

    private String gender;

    private String personalEmail;

}
