package com.sqli.safeSeat.services;

import com.sqli.safeSeat.models.Employee;

import java.util.Date;
import java.util.List;
import java.util.Optional;

public interface EmployeeService {
    boolean hasReservationWithin(Employee employee, Date startDate, Date endDate);
    void save(Employee employee);
    Employee findById(int employeeID);
    List<Employee> findAll();
}
