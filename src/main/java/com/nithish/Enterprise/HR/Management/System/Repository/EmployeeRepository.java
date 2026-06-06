package com.nithish.Enterprise.HR.Management.System.Repository;

import com.nithish.Enterprise.HR.Management.System.Entity.Employee;
import com.nithish.Enterprise.HR.Management.System.Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface EmployeeRepository extends JpaRepository<Employee,Long> {

    Optional<Employee> findByUser(User user);
}