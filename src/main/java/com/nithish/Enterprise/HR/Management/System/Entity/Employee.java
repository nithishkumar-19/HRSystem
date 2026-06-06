package com.nithish.Enterprise.HR.Management.System.Entity;

import com.nithish.Enterprise.HR.Management.System.Audit.Auditable;
import com.nithish.Enterprise.HR.Management.System.Enum.EmployeeStatus;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "employees")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Employee extends Auditable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String employeeCode;

    private String firstName;

    private String lastName;

    private String gender;

    private LocalDate dob;

    private String mobile;

    private String personalEmail;

    private String companyEmail;

    private String department;

    private String designation;

    private LocalDate joiningDate;

    @Enumerated(EnumType.STRING)
    private EmployeeStatus status;

    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany(mappedBy = "employee", cascade = CascadeType.ALL)
    private List<Experience> experiences;

    @OneToMany(mappedBy = "employee", cascade = CascadeType.ALL)
    private List<Skill> skills;

    @OneToMany(mappedBy = "employee", cascade = CascadeType.ALL)
    private List<EmployeeDocument> documents;

    @OneToOne(mappedBy = "employee", cascade = CascadeType.ALL)
    private LeaveBalance leaveBalance;

    @OneToMany(mappedBy = "employee", cascade = CascadeType.ALL)
    private List<LeaveRequest> leaveRequests;

    @OneToMany(mappedBy = "employee", cascade = CascadeType.ALL)
    private List<Certification> certifications;

    @OneToMany(mappedBy = "employee", cascade = CascadeType.ALL)
    private List<Payslip> payslips;

    @OneToOne(mappedBy = "employee", cascade = CascadeType.ALL)
    private Address address;

    @OneToMany(mappedBy = "employee", cascade = CascadeType.ALL)
    private List<Education> educations;
}