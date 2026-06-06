package com.nithish.Enterprise.HR.Management.System.Config;

import com.nithish.Enterprise.HR.Management.System.Entity.Employee;
import com.nithish.Enterprise.HR.Management.System.Entity.User;
import com.nithish.Enterprise.HR.Management.System.Enum.EmployeeStatus;
import com.nithish.Enterprise.HR.Management.System.Enum.Role;
import com.nithish.Enterprise.HR.Management.System.Repository.EmployeeRepository;
import com.nithish.Enterprise.HR.Management.System.Repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
@RequiredArgsConstructor
public class InitialDataLoader implements CommandLineRunner {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final EmployeeRepository employeeRepository;



    @Override
    public void run(String... args) {

        if (userRepository.findByUsername("Harish").isEmpty()) {

            User hr = User.builder()
                    .username("Harish")
                    .password(passwordEncoder.encode("H@123"))
                    .role(Role.HR)
                    .enabled(true)
                    .build();

            userRepository.save(hr);

            //save employee
            Employee empHr = Employee.builder()
                    .employeeCode("EMP001")
                    .firstName("Harish")
                    .lastName("Kumar")
                    .gender("Male")
                    .dob(LocalDate.of(1998, 5, 10))
                    .mobile("9876543210")
                    .personalEmail("harish@gmail.com")
                    .companyEmail("harish@company.com")
                    .department("Human Resources")
                    .designation("HR Manager")
                    .joiningDate(LocalDate.of(2024, 1, 15))
                    .status(EmployeeStatus.COMPLETED)
                    .user(hr)
                    .build();

            employeeRepository.save(empHr);
        }

        if (userRepository.findByUsername("Sathish").isEmpty()) {

            User manager = User.builder()
                    .username("Sathish")
                    .password(passwordEncoder.encode("S@123"))
                    .role(Role.MANAGER)
                    .enabled(true)
                    .build();

            userRepository.save(manager);

            Employee empMgr = Employee.builder()
                    .employeeCode("EMP002")
                    .firstName("Sathish")
                    .lastName("Kumar")
                    .gender("Male")
                    .dob(LocalDate.of(1998, 6, 10))
                    .mobile("9876543210")
                    .personalEmail("sathish@gmail.com")
                    .companyEmail("sathish@company.com")
                    .department("Software Development")
                    .designation("Tech Lead")
                    .joiningDate(LocalDate.of(2024, 1, 15))
                    .status(EmployeeStatus.COMPLETED)
                    .user(manager)
                    .build();

            employeeRepository.save(empMgr);
        }

        if (userRepository.findByUsername("Vignesh").isEmpty()) {

            User employee = User.builder()
                    .username("Vignesh")
                    .password(passwordEncoder.encode("V@123"))
                    .role(Role.EMPLOYEE)
                    .enabled(true)
                    .build();

            userRepository.save(employee);

            Employee empEmployee = Employee.builder()
                    .employeeCode("EMP003")
                    .firstName("Vignesh")
                    .lastName("Kumar")
                    .gender("Male")
                    .dob(LocalDate.of(1998, 7, 10))
                    .mobile("987654327")
                    .personalEmail("vignesh@gmail.com")
                    .companyEmail("vignesh@company.com")
                    .department("Software Tester")
                    .designation("QA Test Engineer")
                    .joiningDate(LocalDate.of(2024, 1, 15))
                    .status(EmployeeStatus.COMPLETED)
                    .user(employee)
                    .build();

            employeeRepository.save(empEmployee);
        }

        System.out.println("Default Users Created Successfully");
    }
}
