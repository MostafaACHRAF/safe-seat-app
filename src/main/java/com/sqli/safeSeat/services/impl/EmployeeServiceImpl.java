package com.sqli.safeSeat.services.impl;

import com.sqli.safeSeat.models.Employee;
import com.sqli.safeSeat.repositories.EmployeeRepository;
import com.sqli.safeSeat.services.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service public class EmployeeServiceImpl implements EmployeeService {

    private EmployeeRepository employeeRepository;

    @Autowired public EmployeeServiceImpl(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    @Override public boolean hasReservationWithin(Employee employee, Date startDate, Date endDate) {
        employee.getReservations().forEach(reservation -> {
            //if startDate within an other reservation period
            //or endDate within an other reservation period
            if (true) {

            }
        });
        return false;
    }

    @Override public void save(Employee employee) {
        this.employeeRepository.save(employee);
    }

    @Override public Employee findById(int employeeId) {
        Optional<Employee> searchableEmployee = this.employeeRepository.findById(employeeId);
        return searchableEmployee.orElse(null);
    }

    @Override public List<Employee> findAll() {
        return this.employeeRepository.findAll();
    }

}
