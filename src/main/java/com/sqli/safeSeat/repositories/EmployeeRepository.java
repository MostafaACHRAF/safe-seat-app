package com.sqli.safeSeat.repositories;

import com.sqli.safeSeat.models.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmployeeRepository extends JpaRepository<Employee, Integer> {}
