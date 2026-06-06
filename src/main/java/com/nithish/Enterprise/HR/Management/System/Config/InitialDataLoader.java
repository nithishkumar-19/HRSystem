package com.nithish.Enterprise.HR.Management.System.Config;

import com.nithish.Enterprise.HR.Management.System.Entity.User;
import com.nithish.Enterprise.HR.Management.System.Enum.Role;
import com.nithish.Enterprise.HR.Management.System.Repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class InitialDataLoader implements CommandLineRunner {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

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
        }

        if (userRepository.findByUsername("Sathish").isEmpty()) {

            User manager = User.builder()
                    .username("Sathish")
                    .password(passwordEncoder.encode("S@123"))
                    .role(Role.MANAGER)
                    .enabled(true)
                    .build();

            userRepository.save(manager);
        }

        if (userRepository.findByUsername("Vignesh").isEmpty()) {

            User employee = User.builder()
                    .username("Vignesh")
                    .password(passwordEncoder.encode("V@123"))
                    .role(Role.EMPLOYEE)
                    .enabled(true)
                    .build();

            userRepository.save(employee);
        }

        System.out.println("Default Users Created Successfully");
    }
}
