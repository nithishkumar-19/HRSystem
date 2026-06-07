package com.nithish.Enterprise.HR.Management.System.Dto;

import jakarta.validation.constraints.*;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PayslipDTO {

    @NotNull(message = "Employee ID required")
    private Long employeeId;

    private String Name;

    @NotBlank(message = "Month required")
    private String month;

    @NotNull(message = "Year required")
    private Integer year;

    @Min(value = 0, message = "Basic salary cannot be negative")
    private Double basicSalary;

    @Min(value = 0)
    private Double allowances;

    @Min(value = 0)
    private Double deductions;
}
