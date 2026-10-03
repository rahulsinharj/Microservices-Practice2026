package com.rahulmicroservices.employee_service.controller;

import com.rahulmicroservices.employee_service.model.Employee;
import com.rahulmicroservices.employee_service.repository.EmployeeRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/employee")
public class EmployeeController {

    private static final Logger LOGGER = LoggerFactory.getLogger(EmployeeController.class);

    @Autowired
    private EmployeeRepository employeeRepository;

    @PostMapping
    public Employee add(@RequestBody Employee employee) {
        System.out.println("Adding employee: " + employee);
        LOGGER.info("Adding employee: {}", employee);
        return employeeRepository.addEmployee(employee);
    }

    @GetMapping
    public List<Employee> findAll() {
        LOGGER.info("Finding all employees");
        return employeeRepository.findAll();
    }

    @GetMapping("/{id}")
    public Employee findById(@PathVariable("id") Long id) {
        LOGGER.info("Finding employee by id: {}", id);
        return employeeRepository.findById(id);
    }

    @GetMapping("/department/{departmentId}")
    public List<Employee> findByDepartment(@PathVariable("departmentId") Long departmentId) {
        LOGGER.info("Finding employees by department id: {}", departmentId);
        return employeeRepository.findByDepartment(departmentId);
    }
}
