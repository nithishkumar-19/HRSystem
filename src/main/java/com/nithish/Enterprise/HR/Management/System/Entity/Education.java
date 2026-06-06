package com.nithish.Enterprise.HR.Management.System.Entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "educations")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Education {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String degree;
    private String institution;
    private String year;

    @ManyToOne
    @JoinColumn(name = "employee_id")
    private Employee employee;
}