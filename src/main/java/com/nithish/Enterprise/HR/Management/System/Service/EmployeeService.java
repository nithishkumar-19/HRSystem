package com.nithish.Enterprise.HR.Management.System.Service;

import com.nithish.Enterprise.HR.Management.System.Dto.EmployeeDTO;
import com.nithish.Enterprise.HR.Management.System.Dto.EmployeeResponse;
import com.nithish.Enterprise.HR.Management.System.Entity.Employee;
import com.nithish.Enterprise.HR.Management.System.Entity.User;
import com.nithish.Enterprise.HR.Management.System.Enum.DocumentStatus;
import com.nithish.Enterprise.HR.Management.System.Enum.EmployeeStatus;
import com.nithish.Enterprise.HR.Management.System.Enum.Role;
import com.nithish.Enterprise.HR.Management.System.Exception.ResourceNotFoundException;
import com.nithish.Enterprise.HR.Management.System.Mapper.UserMapper;
import com.nithish.Enterprise.HR.Management.System.Repository.EmployeeRepository;
import com.nithish.Enterprise.HR.Management.System.Repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EmployeeService {

    private final EmployeeRepository employeeRepository;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserMapper userMapper;

    public Employee createEmployee(EmployeeDTO dto) {

        if (userRepository.findByUsername(dto.getPersonalEmail()).isPresent()) {
            throw new RuntimeException("User already exists");
        }

        // 1. CREATE USER
        User user = User.builder()
                .username(dto.getPersonalEmail())
                .password(passwordEncoder.encode("Temp@123"))
                .role(Role.EMPLOYEE)
                .enabled(true)
                .build();

        user = userRepository.save(user);

        // 2. CREATE EMPLOYEE + LINK USER
        Employee employee = Employee.builder()
                .employeeCode("EMP" + System.currentTimeMillis())
                .firstName(dto.getFirstName())
                .lastName(dto.getLastName())
                .gender(dto.getGender())
                .dob(dto.getDob())
                .mobile(dto.getMobile())
                .personalEmail(dto.getPersonalEmail())
                .companyEmail(dto.getCompanyEmail())
                .department(dto.getDepartment())
                .designation(dto.getDesignation())
                .joiningDate(dto.getJoiningDate())
                .status(EmployeeStatus.COMPLETED)

                // 🔥 THIS IS THE MOST IMPORTANT LINE
                .user(user)

                .build();

        return employeeRepository.save(employee);
    }

    public List<EmployeeResponse> getAllEmployees() {
        return  userMapper.toDto(employeeRepository.findAll()) ;
    }

    public Employee getEmployee(Long id) {
        return employeeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Employee not found: " + id));
    }

    public void updateOnboardingStatus(Long employeeId) {

        Employee employee = employeeRepository.findById(employeeId)
                        .orElseThrow(() -> new ResourceNotFoundException("Employee not found: " + employeeId));

        boolean profileCompleted = employee.getAddress() != null && employee.getEducations() != null && !employee.getEducations().isEmpty();

        boolean documentsApproved = employee.getDocuments() != null && employee.getDocuments().stream().allMatch(doc -> doc.getStatus() == DocumentStatus.APPROVED);

        if (profileCompleted && documentsApproved) {
            employee.setStatus(EmployeeStatus.COMPLETED);
        }

        else {
            employee.setStatus(EmployeeStatus.IN_PROGRESS);
        }

        employeeRepository.save(employee);
    }
}