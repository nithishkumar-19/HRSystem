package com.nithish.Enterprise.HR.Management.System.Dto;

import jakarta.validation.constraints.*;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EmployeeDTO {

    @NotBlank(message = "First name cannot be empty")
    private String firstName;

    @NotBlank(message = "Last name cannot be empty")
    private String lastName;

    @Email(message = "Invalid email format")
    @NotBlank(message = "Personal email is required")
    private String personalEmail;

    @Email(message = "Invalid email format")
    private String companyEmail;

    @Pattern(regexp = "^[0-9]{10}$", message = "Phone must be 10 digits")
    private String mobile;

    private String gender;

    private LocalDate dob;

    @NotBlank(message = "Department is required")
    private String department;

    private String designation;

    private LocalDate joiningDate;
}