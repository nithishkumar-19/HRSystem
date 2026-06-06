package com.nithish.Enterprise.HR.Management.System.Entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "skills")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Skill {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String skillName;

    private String proficiencyLevel;

    @ManyToOne
    @JoinColumn(name = "employee_id")
    private Employee employee;
}
