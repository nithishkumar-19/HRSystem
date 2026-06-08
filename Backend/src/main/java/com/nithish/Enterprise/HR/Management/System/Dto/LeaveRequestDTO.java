package com.nithish.Enterprise.HR.Management.System.Dto;

import jakarta.validation.constraints.*;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LeaveRequestDTO {

    @NotNull(message = "Employee ID required")
    private Long employeeId;

    @NotNull(message = "From date required")
    private LocalDate fromDate;

    @NotNull(message = "To date required")
    private LocalDate toDate;

    @NotBlank(message = "Reason required")
    private String reason;

    @Min(value = 1, message = "Minimum 1 day leave required")
    private Integer noOfDays;
}